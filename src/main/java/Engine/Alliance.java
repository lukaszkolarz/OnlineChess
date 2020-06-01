package Engine;

import java.lang.*;
public enum Alliance {
    WHITE{
        @Override
        public boolean isWhite()
        {
            return true;
        }
        @Override
        public boolean isBlack()
        {
            return false;
        }
        @Override
        public String toStringAlliance()
        {
            return "White";
        }
        },
    BLACK{
        @Override
        public boolean isWhite()
        {
            return false;
        }
        @Override
        public boolean isBlack()
        {
            return true;
        }
        @Override
        public String toStringAlliance()
        {
            return "Black";
        }

    };
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract String toStringAlliance();
}

