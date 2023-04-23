package gb.lib.common.entities;

public enum AgeRating {
    G {
        @Override
        public String defaultDescription() {
            return "These books are for all readers. No content is questionable.";
        }
    },
    PG {
        @Override
        public String defaultDescription() {
            return "These books are for all readers. No content is questionable.";
        }
    },
    PG13 {
        @Override
        public String defaultDescription() {
            return "These books are for mature readers aged 13 and up. Items include frequent language, long potentially graphic battle sequences, and some or strong sex innuendos and discussion of sex.";
        }
    },
    R {
        @Override
        public String defaultDescription() {
            return "These books are for adults only and not the faint of heart due to their content. Content may include pornographic sexual content, pervasive language, and realistic violence like torture and prolonged war sequences.";
        }
    };
    public abstract String defaultDescription();
}
