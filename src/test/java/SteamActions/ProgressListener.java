package SteamActions;

@FunctionalInterface
public interface ProgressListener {

    void onProgress(String currentGame, int currentIndex, int totalGames);
}
