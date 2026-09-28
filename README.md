# Steam Discount & Price Scraper

An automated Java-based desktop application and web scraping utility designed to batch-check game prices, discount statuses, and store availability on Steam. The tool reads game titles from a plain-text file, automates search queries via Selenium WebDriver, dynamically handles age-gate verifications, presents structured data in an interactive Swing GUI with clickable links, and exports spreadsheet-ready CSV files.

---

## Key Features

- **Batch Processing**: Reads a comma-separated list of game titles from a local `.txt` file and processes multiple games in sequence.
- **Web Automation**:
  - Built on Selenium WebDriver with Chrome/Chromium (Brave Browser) support.
  - Page Object Model (POM) architecture cleanly separating page interactions (`SteamHomePage`, `SteamSearchPage`, `SteamGamePage`, `AgeCheckPage`, `NoGamePage`).
  - Automatic handling of age verification gates and missing search results.
  - Headless execution configuration for fast, background scraping without visual browser clutter.
- **Interactive Swing GUI**:
  - File picker interface (`MainWindow`) to select game list files.
  - Responsive background scraping executed asynchronously via `SwingWorker` to keep the UI fluid and prevent freezing.
  - Live progress dialog (`ProgressDialog`) displaying current game status, percentage, and deterministic progress bar.
  - Formatted results table with clickable hyperlinks that launch Steam store pages directly in your default system browser.
- **Spreadsheet-Ready CSV Export**:
  - Automatically exports results to `gameList.csv` on the user's Desktop.
  - Formats store URLs using Excel-compatible `=HYPERLINK()` formulas for immediate spreadsheet navigation.

---

## Architecture & Codebase Structure

The project follows the Page Object Model (POM) pattern alongside modular Swing desktop components:

```
NewsFinder/
├── resources/
│   ├── gameSources/
│   │   └── gameList.txt           # Sample input list of video games
│   └── screenshots/               # Directory for test failure screenshots
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── com/
│   │       │   ├── base/
│   │       │   │   └── BasePage.java            # Core WebDriver wrapper and locator helpers
│   │       │   ├── Objects/
│   │       │   │   └── GameItem.java            # Model representing game pricing, discount, and links
│   │       │   └── steamPages/
│   │       │       ├── AgeCheckPage.java        # Handles Steam age verification screens
│   │       │       ├── NoGamePage.java          # Handles fallback when titles are not found
│   │       │       ├── SteamDiscountsPage.java  # Steam discounts section interactions
│   │       │       ├── SteamGamePage.java       # Extracts pricing and discount details from game pages
│   │       │       ├── SteamHomePage.java       # Interacts with search bar and main navigation
│   │       │       └── SteamSearchPage.java     # Selects games from search result listings
│   │       └── Utilities/
│   │           ├── ActionsUtility.java          # Selenium Actions helper methods
│   │           ├── HyperlinkRenderer.java       # Custom JTable cell renderer for clickable links
│   │           ├── Utility.java                 # File I/O, path helpers, and common utilities
│   │           └── waitUtility.java             # Fluent and explicit WebDriver wait helpers
│   └── test/
│       └── java/
│           ├── Base/
│           │   └── BaseTest.java                # Base TestNG setup and screenshot handlers
│           ├── SteamActions/
│           │   ├── ProgressListener.java        # Callback interface for progress notifications
│           │   ├── SearchForGames.java          # Core scraping orchestration and CSV writer
│           │   └── SearchForGamesTest.java      # TestNG test suite for scraping workflows
│           └── windows/
│               ├── MainWindow.java              # Primary Swing UI window and table viewer
│               └── ProgressDialog.java          # Secondary live progress indicator
└── pom.xml                                      # Maven project configuration and dependencies
```

---

## Prerequisites

Before running the application, ensure the following are installed and configured:

1. **Java Development Kit (JDK)**: JDK 21 or higher (configured for modern Java runtime).
2. **Apache Maven**: Version 3.8+ for dependency management and building.
3. **Chromium-based Browser**:
   - Google Chrome or Brave Browser.
   - By default, the application is configured to locate Brave Browser at:
     `C:\Program Files\BraveSoftware\Brave-Browser\Application\brave.exe`
   - If using Google Chrome or a custom location, update the binary path in `SearchForGames.java` and `BaseTest.java`.

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/NewsFinder.git
cd NewsFinder
```

### 2. Configure Browser Binary Path (Optional)

In `src/test/java/SteamActions/SearchForGames.java` (line 37):

```java
// For Brave Browser (default):
options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");

// For Standard Google Chrome (comment out or point to chrome.exe):
// options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
```

### 3. Build the Project

```bash
mvn clean compile
```

---

## How to Use

### Running the Application via GUI

1. Launch `MainWindow.java` (located under `src/test/java/windows/MainWindow.java`).
2. Click **"Search..."** to open the file chooser dialog.
3. Select your `.txt` input file containing comma-separated game titles (e.g., `resources/gameSources/gameList.txt`).
4. Click **"Look for discounts"**.
5. A secondary progress dialog will appear, reporting real-time progress as each game is verified on Steam.
6. Once finished:
   - Results populate the interactive table with columns: `Game name`, `Price`, `Discounted?`, and `Link`.
   - Click any link in the **Link** column to open that store page in your web browser.
   - A CSV export (`gameList.csv`) is automatically saved to your Desktop.

---

## Input & Output Formats

### Input Format (`.txt`)

A simple text file containing video game titles separated by commas:

```text
Dark Souls III, Counter-Strike 2, Cyberpunk 2077, Elden Ring, Baldur's Gate 3, Hollow Knight, Hades
```

### Output Format (`gameList.csv`)

The generated CSV file follows standard RFC 4180 rules with Excel formula integration:

| Game Name | Game Regular Price | Final Price | Link | Discount |
| :--- | :--- | :--- | :--- | :--- |
| `"Elden Ring"` | `"$59.99"` | `"$35.99"` | `"=HYPERLINK(""https://store.steampowered.com/app/1245620/ELDEN_RING/"", ""Elden Ring"")"` | `Discounted` |
| `"Dark Souls III"` | `"$59.99"` | `"$59.99"` | `"=HYPERLINK(""https://store.steampowered.com/app/374320/DARK_SOULS_III/"", ""Dark Souls III"")"` | `Not discounted` |

---

## Running Automated Tests

Run the test suite using Maven:

```bash
mvn test
```

Test results and automated failure screenshots are captured in `resources/screenshots/`.

---

## Dependencies & Technologies

- **Java**: Core programming language.
- **Selenium Java** (`4.48.0`): Browser automation and DOM parsing.
- **TestNG** (`7.10.2`): Testing framework and test runner.
- **Java Swing (AWT/Swing)**: Desktop GUI and event handling.
- **Maven**: Build automation and dependency resolution.
