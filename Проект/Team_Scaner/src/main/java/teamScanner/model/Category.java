package teamScanner.model;

public enum Category {
    FOOTBALL{
        @Override
        public String toString() {
            return "Футбол";
        }
    }, BASKETBALL{
        @Override
        public String toString() {
            return "Баскетбол";
        }
    }, VOLLEYBALL{
        @Override
        public String toString() {
            return "Волейбол";
        }
    }, HOCKEY{
        @Override
        public String toString() {
            return "";
        }
    }
}
