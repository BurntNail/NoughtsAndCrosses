public class helper {

    public static String getTextPlaying (SquareEnum state)
    {
        switch(state)
        {
            case unactive:
                return "Not Chosen";
            case cross:
                return "X";
            case nought:
                return "O";
        }

        return null;
    }



    public static String getXY (int x, int y)
    {
        return "(" + x + ", " + y + ")";
    }

    public static SquareEnum getPlayer (boolean isNoughts)
    {
        if(isNoughts)
            return SquareEnum.nought;
        else
            return SquareEnum.cross;
    }

    public static SquareEnum getSquareEnumFromPlayer (boolean isNoghts)
    {
        if(isNoghts)
            return SquareEnum.nought;
        else
            return SquareEnum.cross;
    }


}
