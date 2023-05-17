interface FrontendInterface {
    // public FrontendInterface(Scanner in, BackendInterface backend);
    public void runCommandLoop();
    public char mainMenuPrompt();
    public void loadDataCommand();
    public void listLocations();
    public void findShortestPath();
}

