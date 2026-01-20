public final class Main {

    public static void main(String[] args) {
        int exit = run(args);
        if (exit != 0) {
            System.exit(exit);
        }
    }

    static int run(String[] args) {
        CliOptions opt;
        try {
            opt = CliOptions.parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println("Argument error: " + e.getMessage());
            printUsage();
            return 2;
        }

        ProcessingResult result = new FileProcessor().process(opt);

        String stats = StatsPrinter.format(opt.getStatsMode(), result);
        if (!stats.isEmpty()) {
            System.out.print(stats);
        }

        if (!result.getErrors().isEmpty()) {
            System.err.println("Completed with errors:");
            for (String err : result.getErrors()) {
                System.err.println(" - " + err);
            }
            return 1;
        }

        return 0;
    }

    private static void printUsage() {
        System.err.println("Usage: java -jar app.jar [-a] [-s|-f] [-o <dir>] [-p <prefix>] <inputFiles...>");
    }
}
