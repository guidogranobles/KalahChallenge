var stompClient = null;
var Player2 = null;
var Player1 = null;
var gameStarted = false;


var gamePlayUpdate = {
		idFirstPlayer: 0,
		idSecondPlayer: 0,
		idBoard: '',
		pitToEmpty: 0
}


function connect() {
	
	setCurrentUserPlayer1();

	var socket = new SockJS('/kalah-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		
		console.log('Connected: ' + frame);
		
		stompClient.subscribe('/user/' + Player1 + '/queue/playerUpdates',
			function(message) {
				updatePlayers(JSON.parse(message.body));
		});
		stompClient.subscribe('/queue/playerUpdates', function(message) {
			updatePlayers(JSON.parse(message.body));
		});

		getAvailablePlayers();
	}, function(message){
		console.log(message);
		/*if(message.indexOf("Lost connection") !== -1){
			connectionLostHandler();
		}*/
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function setCurrentUserPlayer1(){
	let idx = $("#currentUser").text().indexOf(":") + 1;
	let curUser = $("#currentUser").text().substr(idx).trim();
	Player1 = curUser;
}

function getAvailablePlayers() {
	stompClient.send("/app/availablePlayersWS", {});
}

function startNewGame(player2){
	stompClient.send("/app/startGame", {}, player2);
}

function playGame(updateInfo){
	stompClient.send("/app/updatePlayerBoard", {}, JSON.stringify(updateInfo));
}

function notifySecondPlayer(gamePlayStatus){
	stompClient.send("/app/notifySecondPlayer", {}, JSON.stringify(gamePlayStatus));
}


function sendInvitationToPlayer(playerDest) {
	
	//reset player 1
	setCurrentUserPlayer1();

	$('#waitPlayerResponse').modal('show');
	Player2 = playerDest;
	stompClient.send("/app/invitationToPlay", {}, playerDest);
	
	setTimeout(function() {
		if(!gameStarted){
			$('#waitPlayerResponse').modal('hide');
			$('#resInvitation').html('We are sorry. We did not get any response from player ' + Player2);
			$('#dlgNotResponse').modal('show');
		}
	}, 15000);


}

function resInvitationToPlay(playerDest, resInvitation) {
	stompClient.send("/app/resInvitationToPlay", {}, playerDest + '-'
			+ resInvitation);
}

//List players currently connected and ready to play. 
function updateAvaliablePlayersList(players) {

	let divPlayers = '';
	let idx = $("#currentUser").text().indexOf(":") + 1;
	let curUser = $("#currentUser").text().substr(idx).trim();

	players
			.forEach(function(playerName) {
				if (playerName !== curUser) {

					divPlayers += '<div  class="col-sm-1 col-xs-6 col-md-1"><img src="http://placehold.it/64x64" alt="Generic placeholder thumbnail" class="thumbnail"'
							+ 'style="margin-bottom: 0"> <div class="col-md-1 col-xs-6 caption text-center">'
							+ playerName + '<p>';
					if (!gameStarted) {
						//Player should not be possible to send other invitation if a game already started.
						divPlayers += '<button class="btn btn-xs btn-primary btnInvite" role="button" onClick="sendInvitationToPlayer(&quot;'
								+ playerName + '&quot;)"> Invite </button>';
					}

					divPlayers += '</p></div></div>';
				}

			});

	$("#availablePlayers").html(divPlayers);
}


function incomingInvitationHandler(playerOrigin){
	
	//reset player 1
	setCurrentUserPlayer1();
	
	$("#playerInvitation").html(
			"You have an invitation to play from " + playerOrigin);
	
	$('#dlgInvitation .modal-footer button').on('click', function(event) {
		var $button = $(event.target);
		$(this).closest('.modal').one('hidden.bs.modal', function() {
			if ($button[0].id === 'Accept') {
				/*We need to keep the same order in both sides, so 
				* Player2 is always who recived the invitation.
				* Player1 is always who sent the invitation.**/
				Player2 = Player1;
				Player1 = playerOrigin
				//Just for the view remote player is always who sent the invitation
				$("#remotePlayer").html("Player 2: " + Player1);
				$("#waitPlayerMove").show();
				$(".btnInvite").hide();
			}
			resInvitationToPlay(playerOrigin, $button[0].id);
		});
	});
	
	$("#dlgInvitation").modal();
	//If user doesn't accept the invitation on time just hide the window, 
	//it will be like rejecting the invitation.
	setTimeout(function() {
		if($("#dlgInvitation").is(':visible')){
		  $("#dlgInvitation").modal('hide');
		}
	}, 10000);

}

function invitationResponseHandler(response) {

	$('#waitPlayerResponse').modal('hide');
	if (response === 'Accept') {
		$(".btnInvite").hide();
		startNewGame(Player2);
	}else{
		$("#resInvitation").html('Player ' + Player2 + ' rejected the invitation');
		$("#dlgNotResponse").modal();
	}
}

function disableAllpits(){	
	for (var i = 1; i <= 12; i++) {
		$("#pit" + i).prop('disabled', true);
		$("#pit" + i).css("background-color", "#33daff");	
		$("#home1").css("background-color", "#33daff");	
		$("#home2").css("background-color", "#33daff");	

	}
}

function enablePitsPlayer(curPlayer) {
/*Pits from 1 to 6 are the pits in bottom of the page,
 * hence pits on the top are pits from 7 to 12.
 */
	if (curPlayer === Player1) {
		for (var i = 1; i <= 6; i++) {
			$("#pit" + i).prop('disabled', false);
			$("#pit" + i).removeClass('disabled');
			$("#pit" + i).css("background-color", "#14bf88");	
			$("#home1").css("background-color", "#14bf88");	

		}

		for (var i = 7; i <= 12; i++) {
			$("#pit" + i).prop('disabled', true);
			$("#pit" + i).addClass('disabled');
			$("#pit" + i).css("background-color", "#33daff	");
			$("#home2").css("background-color", "#33daff");	
		}

	}else{
		for (var i = 1; i <= 6; i++) {
			$("#pit" + i).prop('disabled', true);
			$("#pit" + i).addClass('disabled');
			$("#pit" + i).css("background-color", "#33daff");	
			$("#home1").css("background-color", "#33daff");	

		}

		for (var i = 7; i <= 12; i++) {
			$("#pit" + i).prop('disabled', false);
			$("#pit" + i).removeClass('disabled');
			$("#pit" + i).css("background-color", "#14bf88");	
			$("#home2").css("background-color", "#14bf88");	

		}
	}
}

function initGame(curState){
	
	if(curState.currentPlayer === Player1){
		$("#remotePlayer").html("Player 2: " + Player2);
		$("#yourTurn").show();
		enablePitsPlayer(Player1);
	}
	
	gameStarted = true;
	gamePlayUpdate.idFirstPlayer=curState.idPlayer1;
	gamePlayUpdate.idSecondPlayer=curState.idPlayer2;
	gamePlayUpdate.idBoard=curState.idBoard;	
	
	for (var i = 1; i <= 12; i++) {
		$("#pit" + i).html('6');
	}
	$("#home1").html('0');
	$("#home2").html('0');
}


function play(pitNumber){
	
	//Empty pits no valid
	if($("#pit" + pitNumber).text() !== "0"){
	
		/*In the server the model only knows pits from 1 to 6 for each player*/
		if(pitNumber < 7){
			gamePlayUpdate.pitToEmpty = pitNumber;	
		}else{
			gamePlayUpdate.pitToEmpty = pitNumber-6;	
		}
		
		console.log(gamePlayUpdate);
		playGame(gamePlayUpdate);
	
	}
}

function checkForWinner(newGameState){
		if(newGameState.status==="winner"){
			
			disableAllpits();
			$("#yourTurn").hide();
			$("#waitPlayerMove").hide();
			$("#gameOver").html("Player " + newGameState.nameWinner + " has won!!" )
			$("#dlgWinner").modal();
			$(".btnInvite").show();
			gameStarted = false;
			getAvailablePlayers();
			return true;
		}
		
		return false;
}

function updateStonesPits(newGameState){
	for (var i = 1; i <= 6; i++) {
		$("#pit" + i).html(newGameState.boardPlayer1["pit"+i]);
		$("#pit" + (i + 6)).html(newGameState.boardPlayer2["pit"+i]);
		$("#home1").html(newGameState.boardPlayer1.kalah);
		$("#home2").html(newGameState.boardPlayer2.kalah);
	}
	
}

function updateMovementPlayerInfo(status){
	
	if(status !== "repeat"){
		if($("#yourTurn").is(':visible') || $("#playerRepeat").is(':visible')){
			$("#yourTurn").hide();
			$("#playerRepeat").hide();
			$("#waitPlayerMove").show();
		}else{
			$("#yourTurn").show();
			$("#waitPlayerMove").hide();
			$("#remotePlayerRepeat").hide();
		}
	}else{
		if($("#yourTurn").is(':visible')){
			$("#yourTurn").hide();
			$("#playerRepeat").show();
		}else if($("#remotePlayerRepeat").is(':hide')){
			$("#remotePlayerRepeat").show();
			$("#waitPlayerMove").hide();	
		}
	}
}

function updateGame(newGameState){
		updateStonesPits(newGameState);
		updateMovementPlayerInfo(newGameState.status);	
}

function gameStateHandler(newGameState){
	updateGame(newGameState);
	checkForWinner(newGameState)
	if(newGameState.currentPlayer===Player1){
		newGameState.currentPlayer = Player2;
	}else{
		newGameState.currentPlayer = Player1;
	}
	
	if(newGameState.status !== "repeat"){
		disableAllpits();
	}

	notifySecondPlayer(newGameState);
}


function nextMoveHandler(newGameState){
	updateGame(newGameState);
	if(newGameState.status !== "repeat"){
		enablePitsPlayer(newGameState.currentPlayer);		
	}
	checkForWinner(newGameState)
}

function errorHandler(error){
	$("#dlgError-header").html("Fatal Error!");
	$("#msgError").html(error);
	$("#dlgError").modal();
}

function sessionTimeoutHnadler(){
	console.log('reload page');
	location.reload(true); 
}

function connectionLostHandler(){
	$("#dlgError-header").html("Comunication error");
	$("#msgError").html("Connection with server has been lost. It could be due to session expired");
	$('#dlgError .modal-footer button').on('click', function(event) {
		location.reload(true); 
	});
	$("#dlgError").modal();
}

function updatePlayers(message) {

	switch (message.TYPE) {

	case 'AVAILABLE_PLAYERS':
		updateAvaliablePlayersList(message.PAYLOAD);
		break;
	case 'INVITATION':
		incomingInvitationHandler(message.PAYLOAD)
		break;
	case 'INVITATION_RESPONSE':
		invitationResponseHandler(message.PAYLOAD);
		break;
	case 'GAME_STARTED':
		initGame(message.PAYLOAD);
		break;
	case 'GAME_STATE_CHANGED':
		gameStateHandler(message.PAYLOAD);
		break;
	case 'GAME_NEXT_MOVE':
		nextMoveHandler(message.PAYLOAD);
		break;
	case 'GAME_ERROR':
		errorHandler(message.PAYLOAD);		
	case 'SESSION_TIMEOUT':
		sessionTimeoutHnadler();
	
	}
}

