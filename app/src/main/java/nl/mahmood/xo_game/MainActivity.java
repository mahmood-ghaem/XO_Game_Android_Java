package nl.mahmood.xo_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 means null,
    // 1 means o_dice,
    // 2 means x_dice
    int[] cellsStatus = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    //Winning situations are
    //Horizontal first row : {0, 1, 2},
    //Horizontal second row : {3, 4, 5},
    //Horizontal third row : {6, 7, 8},
    //Vertical first row : {0, 3, 6},
    //Vertical second row : {1, 4, 7},
    //Vertical third row : {2, 5, 8},
    //Multiplication sign : {0, 4, 8},
    //Multiplication sign : {2, 4, 6}
    int[][] winningSituations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int currentPlayer = 1;
    boolean gameActivity = true;

    //This method is called when one player hits each cell
    public void pushDice(View view) {
        ImageView cellImageView = (ImageView) view;

        //catch tag number of hitted imageView
        int hittedImageViewTag = Integer.parseInt(cellImageView.getTag().toString());

        //cellsStatus[hittedImageViewTag] == 0 means the hitted cell is empty
        //gameActivity is true means game not finished yet
        if (cellsStatus[hittedImageViewTag] == 0 && gameActivity) {

            //We can hold player ID in each cell
            // 1 means player O,
            // 2 means player X
            cellsStatus[hittedImageViewTag] = currentPlayer;

            //put imageView out of stage
            cellImageView.setTranslationY(-1500);

            //We check which player has been hitted and display the relevant photo
            if (currentPlayer == 1) {
                cellImageView.setImageResource(R.drawable.o_dice);
                currentPlayer = 2;
            } else {
                cellImageView.setImageResource(R.drawable.x_dice);
                currentPlayer = 1;
            }

            //bring imageView into the stage with animation
            cellImageView.animate().translationYBy(1500).rotation(3600).setDuration(300);

            //We check to see if array cells look like winning modes
            for (int[] situations : winningSituations) {
                if (cellsStatus[situations[0]] == cellsStatus[situations[1]] && cellsStatus[situations[1]] == cellsStatus[situations[2]] && cellsStatus[situations[0]] != 0) {

                    //if somebody won then gameActivity is false
                    gameActivity = false;
                    String winner = "";
                    if (currentPlayer == 1) {
                        winner = "Player X ";
                    } else {
                        winner = "Player O";
                    }

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.summaryTextView);
                    winnerTextView.setText(winner + " has won!");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }else{
            clearTableGame();
        }
    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.summaryTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        clearTableGame();
    }

    public void clearTableGame(){
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        //set to empty all of cells
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView cellImageView = (ImageView) gridLayout.getChildAt(i);
            cellImageView.setImageDrawable(null);
        }
        //set all of cellsStatus array to 0 (null)
        for (int i=0; i<cellsStatus.length; i++) {
            cellsStatus[i] = 0;
        }

        currentPlayer = 1;
        gameActivity = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
