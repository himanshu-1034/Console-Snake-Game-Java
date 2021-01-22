package com.snakeGame;


import java.util.Random;
import java.util.Scanner;

class Snake{
    private int x;
    private int y;
    private char data;
    private Snake next;
    Snake(int x,int y,char data){
        this.x = x;
        this.y = y;
        this.data = data;
        this.next = null;
    }

    public Snake getNext() {
        return next;
    }

    public void setNext(Snake next) {
        this.next = next;
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}


class SnakeLinkedList{
    private Snake head;

    public Snake getHead() {
        return head;
    }

    public void setHead(Snake head) {
        this.head = head;
    }


    void addToFront(int x,int y,char data){
        Snake node = new Snake(x,y,data);
        if (head==null)
            head = node;
        else{
            node.setNext(head);
            head = node;

        }
    }
    void removeFromLast(){

        Snake secondLastNode = head;
        while (secondLastNode.getNext().getNext()!=null){
            secondLastNode= secondLastNode.getNext();

        }
            secondLastNode.setNext(null);
    }


}

class foodCoordinates{
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}



public class Main {

    static int gameScore = 0;

    static foodCoordinates generateFood(SnakeLinkedList list,int row,int col){
        Random random = new Random();
        foodCoordinates coordinates = new foodCoordinates();

        int x = random.nextInt(row);
        int y = random.nextInt(col);

        Snake currNode = list.getHead();
        while (currNode!=null){
            if (currNode.getX()==x&&currNode.getY()==y){
                x = random.nextInt(row);
                y = random.nextInt(col);
                currNode = list.getHead();
            }else {
                currNode=currNode.getNext();
            }
        }

        coordinates.setX(x);
        coordinates.setY(y);

        return coordinates;

    }

    static boolean moveSnake(SnakeLinkedList list,char move,int row,int col,char[][] board){
        int x,y;
        x = list.getHead().getX();
        y = list.getHead().getY();
        int oldX =x;
        int oldY =y;
        int flag=0;
        int nextX = list.getHead().getNext().getX();
        int nextY = list.getHead().getNext().getY();
        switch (move){
            case 'w':
            case 'W':
                if (x<=0){
                    x=9;
                    flag=1;
                }
                else if (nextX!=x-1){
                    x--;
                    flag=1;
                }else {
                    System.out.println("Wrong Move");
                }

                break;
            case 's':
            case 'S':
                if (x>=row-1){
                    x=0;
                    flag=1;
                }
                else if (nextX!=x+1){
                    x++;
                    flag=1;
                }else {
                    System.out.println("Wrong Move");
                }
                break;
            case 'a':
            case 'A':
                if (y<=0){
                    y=9;
                    flag=1;
                }
                else if (nextY != y-1){
                    y--;
                    flag=1;
                }else {
                    System.out.println("Wrong Move");
                }
                break;
            case 'D':
            case 'd':
                if (y>=col-1){
                    y=0;
                    flag=1;
                }else if (nextY != y+1){
                    y++;
                    flag=1;
                }else {
                    System.out.println("Wrong Move");
                }
                break;
            default:
                System.out.println("Wrong Move");
        }



        if (flag==1){

            if (board[x][y]=='0'){


                list.getHead().setData('*');
                list.addToFront(x,y,'%');
                list.removeFromLast();
                for(int i=0;i<row;i++){
                    for (int j=0;j<col;j++){
                        if (board[i][j]=='@')
                            continue;
                        else
                            board[i][j]='0';
                    }
                }

                return true;

            }else if (board[x][y]=='@'){
                list.getHead().setData('*');
                list.addToFront(x,y,'%');
                gameScore++;
                initBoard(board,row,col,list);
                return true;
            }else {
                Snake snake= list.getHead().getNext();
                while (snake!=null){
                    if (snake.getX()==x&&snake.getY()==y){
                        return false;
                    }
                    snake = snake.getNext();
                }

                return false;
            }

        }
        else {
            return true;
        }



    }

    static void initBoard(char[][] board,int row,int col,SnakeLinkedList list){
        for (int i=0;i<row;i++)
            for (int j=0;j<col;j++)
                board[i][j] = '0';

            foodCoordinates coordinates = generateFood(list,row,col);
            board[coordinates.getX()][coordinates.getY()] = '@';
    }

    static void printBoard(char[][] board,int row,int col,SnakeLinkedList list){
        Snake obj = list.getHead();
        while (obj!=null){
            board[obj.getX()][obj.getY()] = obj.getData();
            obj = obj.getNext();
        }
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }


    public static void main(String[] args) {
//        creating board for snake
        char[][] board = new char[10][10];

// initialising snake
        SnakeLinkedList list = new SnakeLinkedList();
        list.addToFront(7,6,'*');
        list.addToFront(6,6,'*');
        list.addToFront(5,6,'%');

        initBoard(board,10,10,list);
        printBoard(board,10,10,list);

        System.out.println("NORMAL W A S D ARE THE CONTROLS . YOU CAN YOU EITHER CAPITAL OR SMALL CASE ALPHABETS HERE. 'Q' IS TO EXIT");

        Scanner sc = new Scanner(System.in);
        char move;
        do {
            move = sc.next().charAt(0);

               boolean response = moveSnake(list,move,10,10,board);
                printBoard(board,10,10,list);
//            System.out.println(response);
                if (!response){

                    System.out.println("GAME OVER");
                    break;
                }

        }while (move!='q'&&move!='Q');

        System.out.println("YOUR SCORE IS : "+gameScore);



    }
}


