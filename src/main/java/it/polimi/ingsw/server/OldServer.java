
    /*
    public static synchronized void startMultiplayerGame(int size) {
        if(size > 1) {
            Game game = Game.getInstance();
            Integer gameId = game.newGame(true);
            //MultiPlayerGameInstance gameInstance = (MultiPlayerGameInstance) game.getGameInstance(gameId);
/*
            for (int i = 0; i < size; i++) {

                TemporaryPlayer tp = temporaryPlayers.removeFirst();
                try {
                    tp.getDataOutputStream().writeUTF("GAME STARTED");
                    System.out.println("Starting game for: " + tp.getNickname());
                    gameInstance.addPlayer(tp.getNickname(), userId.getAndIncrement(), tp.getDataOutputStream(), tp.getDataInputStream());
                } catch (IOException e1) {
                    System.out.println("Exception occurred while handling socket");
                } catch (MaxPlayersException e2) {
                    System.out.println("Maximum number of players reached");
                }
            }

            if (!temporaryPlayers.isEmpty())
                temporaryPlayers.getFirst().setFirstPlayer();

            MultiPlayerManager manager = (MultiPlayerManager) game.getGameManager(gameId);

            MultiPlayerGameHandler gameHandler = new MultiPlayerGameHandler(gameInstance, manager);

            gameHandler.start();

 */


