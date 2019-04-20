import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {

    //Obvious (should be)
    private JFrame selectFrame, watchFrame;
    private GridLayout selectLay, watchLay;
    private Dimension frameDimension, selectDimension;
    private int closeOp, sizeMultiplier, size;

    //Text in watch window
    private JTextField[][] watchText;

    //Buttons for X and O
    private JButton[][] selectButtonsX;
    private JButton[][] selectButtonsO;

    //Text to show grid item
    private JTextField[][] selectText;

    private SquareEnum[][] grid;

    private Font font;

    private String fontName = "Avenir";

    public gui(int size)
    {
        this.size = size;

        //region General Init
        selectFrame = new JFrame("Noughts And Crosses - Select Here");
        watchFrame = new JFrame("Noughts And Crosses - Watch Here");

        sizeMultiplier = 150;
        closeOp = WindowConstants.EXIT_ON_CLOSE;
        font = new Font(fontName, Font.PLAIN, 15);

        frameDimension = new Dimension(size * sizeMultiplier, size * sizeMultiplier);
        selectDimension = new Dimension(frameDimension.width / 3, frameDimension.height / 9);
        selectLay = new GridLayout(size*size, size);
        watchLay = new GridLayout(size, size);

        selectFrame.setResizable(false);
        selectFrame.setPreferredSize(frameDimension);
        selectFrame.setLayout(selectLay);
        selectFrame.setDefaultCloseOperation(closeOp);

        watchFrame.setResizable(false);
        watchFrame.setPreferredSize(frameDimension);
        watchFrame.setLayout(watchLay);
        watchFrame.setDefaultCloseOperation(closeOp);


        watchText = new JTextField[size][size];
        selectButtonsX = new JButton[size][size];
        selectButtonsO = new JButton[size][size];
        selectText = new JTextField[size][size];
        grid = new SquareEnum[size][size];
        //endregion

        //region 2D Array Init
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                int x2 = x + 1;
                int y2 = y + 1;


                selectButtonsO[x][y] = new JButton("O");
                selectButtonsO[x][y].setFont(font);
                selectButtonsO[x][y].setPreferredSize(selectDimension);
                selectButtonsX[x][y] = new JButton("X");
                selectButtonsX[x][y].setFont(font);
                selectButtonsX[x][y].setPreferredSize(selectDimension);
                selectText[x][y] = new JTextField(helper.getXY(x2, y2));
                selectText[x][y].setFont(font);
                selectText[x][y].setPreferredSize(selectDimension);
                watchText[x][y] = new JTextField(" ");
                watchText[x][y].setFont(font);

                selectText[x][y].setEditable(false);
                watchText[x][y].setEditable(false);

                watchFrame.add(watchText[x][y]);

                grid[x][y] = SquareEnum.unactive;

            }
        }

        //endregion

        //Region addToSelectFrame
        for (int x = 0; x < size * size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                selectFrame.add(selectText[x / size][y]);
            }

            for (int y = 0; y < size; y++)
            {
                selectFrame.add(selectButtonsX[x / size][y]);
            }

            for (int y = 0; y < size; y++)
            {
                selectFrame.add(selectButtonsO[x / size][y]);
            }
        }


        //endregion

        //region Button Action Listeners
        for(int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                final int x2 = x;
                final int y2 = y;

                selectButtonsX[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        changeSquare(SquareEnum.cross, x2, y2);
                        didTheyWin();
                    }
                });

                selectButtonsO[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        changeSquare(SquareEnum.nought, x2, y2);
                        didTheyWin();
                    }
                });
            }
        }
        //endregion

        //region JFrame end stuff
        selectFrame.pack();
        selectFrame.setLocationRelativeTo(null);
        selectFrame.setVisible(true);

        watchFrame.pack();
        watchFrame.setLocationRelativeTo(null);
        watchFrame.setVisible(true);
        //endregion
    }

    private void changeSquare (SquareEnum sq, int x, int y)
    {
        watchText[x][y].setText(helper.getTextPlaying(sq));
        grid[x][y] = sq;

        selectButtonsO[x][y].setEnabled(false);
        selectButtonsX[x][y].setEnabled(false);


        switch (sq)
        {
            case cross:
                selectText[x][y].setText("Chosen as X");
                System.out.println(helper.getXY(x, y) + " as " + SquareEnum.cross);
                break;
            case nought:
                System.out.println(helper.getXY(x, y) + " as " + SquareEnum.nought);
                selectText[x][y].setText("Chosen as O");
                break;
        }
    }

    private SquareEnum[][] getGrid ()
    {
        return grid;
    }

    private SquareEnum[] getRow (int row)
    {
        return grid[row];
    }

    private SquareEnum[] getCol (int col)
    {
        SquareEnum[] returner = new SquareEnum[size];

        for (int x = 0; x < size; x++)
        {
            returner[x] = grid[x][col];
        }

        return returner;
    }

    private SquareEnum rowWon (SquareEnum[] row)
    {
        int crosses = 0;
        int noughts = 0;

        for(SquareEnum sq : row)
        {
            if(sq == SquareEnum.unactive)
            {
                return SquareEnum.unactive;
            }

            if(sq == SquareEnum.cross)
                crosses++;
            if(sq == SquareEnum.nought)
                noughts++;
        }

        if(noughts == 0 && crosses == size)
            return SquareEnum.cross;

        if(crosses == 0 && noughts == size)
            return SquareEnum.nought;

        return SquareEnum.unactive;
    }
    private SquareEnum colWon (SquareEnum[] col)
    {
        int crosses = 0;

        for(SquareEnum sq : col)
        {
            if(sq == SquareEnum.unactive)
            {
                return SquareEnum.unactive;
            }

            if(sq == SquareEnum.cross)
                crosses++;


        }

        if(crosses == size)
            return SquareEnum.cross;

        return SquareEnum.nought;
    }

    private void didTheyWin ()
    {
        boolean foundOne = false;

            for (int x = 0; x < size; x++) {
                if(!foundOne) {
                    SquareEnum[] row = getRow(x);

                    switch (rowWon(row)) {
                     case nought:
                            win(SquareEnum.nought);
                            foundOne = true;
                            disableButtonsAndTxt(x, rowOrColumn.row, SquareEnum.nought);
                            break;
                        case cross:
                            win(SquareEnum.cross);
                            foundOne = true;
                            disableButtonsAndTxt(x, rowOrColumn.row, SquareEnum.cross);
                            break;
                }


            }

            for (int y = 0; y < size; y++)
            {
                SquareEnum[] col = getCol(y);

                switch (colWon(col))
                {
                    case nought:
                        win(SquareEnum.nought);
                        foundOne = true;
                        disableButtonsAndTxt(y, rowOrColumn.column, SquareEnum.nought);
                        break;
                    case cross:
                        win(SquareEnum.cross);
                        foundOne = true;
                        disableButtonsAndTxt(y, rowOrColumn.column, SquareEnum.nought);
                        break;
                }
            }

        }
    }

    private void win (SquareEnum type)
    {
        switch (type)
        {
            case cross:
                System.out.println("Crosses Won!!");
                break;
            case nought:
                System.out.println("Noughts Won!!");
                break;
        }
    }

    private void disableButtonsAndTxt (int xOrY, rowOrColumn RC, SquareEnum whoWon)
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                selectButtonsX[x][y].setEnabled(false);
                selectButtonsO[x][y].setEnabled(false);
            }
        }

        switch (RC)
        {
            case row:
                disableX(xOrY, whoWon);
                break;
            case column:
                disableY(xOrY, whoWon);
        }
    }

    private void disableX (int row, SquareEnum whoWon)
    {
        for (int y = 0; y < size; y++)
        {
            watchText[row][y].setText("WON!! as " + helper.getTextPlaying(whoWon));
        }

        for (int x = 0; x < size; x++)
        {
            if(x == row)
                continue;

            for (int y = 0; y < size; y++)
            {
                watchText[x][y].setText("Was " + helper.getTextPlaying(getGrid()[x][y]));
            }
        }
    }

    private void disableY (int col, SquareEnum whoWon)
    {
        for (int x = 0; x < size; x++)
        {
            watchText[x][col].setText("WON!! as " + helper.getTextPlaying(whoWon));
        }

        for (int y = 0; y < size; y++)
        {
            if(y == col)
                continue;

            for (int x = 0; x < size; x++)
            {
                watchText[x][y].setText("Was " + helper.getTextPlaying(getGrid()[x][y]));
            }
        }
    }
}
