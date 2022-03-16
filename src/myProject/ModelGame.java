package myProject;

public class ModelGame {
    private String[][] tableroPosUsuario, tableroPosMaquina, posicionDisparos;
    private String error;
    private Machine machine;

    public ModelGame() {
        //initializes the matrix to "" to paint the representing button as water
        tableroPosUsuario= new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tableroPosUsuario[i][j]="";
            }
        }

        tableroPosMaquina = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tableroPosMaquina[i][j]="";
            }
        }

        machine = new Machine();
    }

    public boolean ingresarBarcoUsuario(int posicionHorizontal,int posicionVertical,String alineacion, String barco){
        return posicionarflota(tableroPosUsuario,posicionHorizontal,posicionVertical,alineacion,barco);
    }

    /**
     * checks if the vessel can be added to the underlying positions
     * @param posicionHorizontal the horizontal position where you want to start painting the boat
     * @param posicionVertical the vertical position where you want to start painting the boat
     * @param alineacion "horizontal" or "vertical"
     * @param barco vessel name to add
     * @return whether it is possible or not
     */
    private boolean posicionarflota(String[][] matrix,int posicionHorizontal,int posicionVertical,String alineacion, String barco){
        boolean answer=false;
        int espacio=0;
        switch (barco){
            case "portaaviones"-> espacio=4;
            case "submarino"-> espacio=3;
            case "destructor"-> espacio=2;
            case "fragata"-> espacio=1;
        }
        //checks if the initial position (marked by the user) is empty
        if (!matrix[posicionHorizontal][posicionVertical].equals("")){
            answer=false;
            error="esta posicion ya esta en uso";
        }

        else if(alineacion.equals("horizontal")){
            //check that the initial position marked by the user part of the fleet is not left out.
            if(posicionHorizontal+espacio>10){
                answer=false;
                error="el "+barco+" ocupa "+espacio+" espacios. Trata con al menos "+(posicionHorizontal+espacio-10)+" posiciones hacia la izquierda.";
            }else {
                for (int i = posicionHorizontal; i < posicionHorizontal + espacio; i++) {
                    //check that all positions where the boat would be are empty.
                    if(!matrix[i][posicionVertical].equals("")){
                        answer=false;
                        error="una de las posiciones que ocuparia tu "+barco+" ya esta en uso.";
                    }
                    //after having carried out all the revisions, it is verified that the boat can be added.
                    else if(i== posicionHorizontal + espacio-1){
                        answer=true;
                        setTableroPosicion(matrix,posicionHorizontal, posicionVertical, alineacion, barco, espacio);
                    }
                }
            }
        }
        //check that the initial position marked by the user part of the fleet is not left out.
        else if(alineacion.equals("vertical")){
            //check that the initial position marked by the user part of the fleet is not left out.
            if(posicionVertical+espacio>10){
                answer=false;
                error="el "+barco+" ocupa "+espacio+" espacios. Trata con al menos "+(posicionVertical+espacio-10)+" posiciones hacia arriba.";
            }else {
                for (int i = posicionVertical; i < posicionVertical + espacio; i++) {
                    //check that all positions where the boat would be are empty.
                    if(!matrix[posicionHorizontal][i].equals("")){
                        answer=false;
                        error="una de las posiciones que ocuparia tu "+barco+" ya esta en uso.";
                    }
                    //after having carried out all the revisions, it is verified that the boat can be added.
                    else if(i== posicionVertical + espacio-1){
                        answer=true;
                        setTableroPosicion(matrix,posicionHorizontal, posicionVertical, alineacion, barco, espacio);
                    }
                }
            }
        }
        return answer;
    }


    /**
     * start adding the boat in the indicated position and alignment
     * @param posicionHorizontal the horizontal position where you want to start painting the boat
     * @param posicionVertical the vertical position where you want to start painting the boat
     * @param alineacion "horizontal" or "vertical"
     * @param barco vessel name to add
     * @param espacio space occupied by the ship
     */
    private void setTableroPosicion(String[][] matrix,int posicionHorizontal,int posicionVertical,String alineacion, String barco,int espacio) {
        int contador=1;
        if (alineacion.equals("horizontal")){
            for (int i = posicionHorizontal; i < posicionHorizontal + espacio; i++) {
                matrix[i][posicionVertical]=barco+".H."+contador;
                contador++;
            }
        }
        else{
            for (int i = posicionVertical; i < posicionVertical + espacio; i++) {
                matrix[posicionHorizontal][i]=barco+".V."+contador;
                contador++;
            }
        }
    }

    /**
     * @return the matrix with the information of where the user's fleet is positioned.
     */
    public String[][] getTableroPosUsuario() {
        return tableroPosUsuario;
    }

    /**
     * @return the reason why the boat could not be added
     */
    public String getError(){
        return error;
    }


    public void ingresarBarcosMaquina(){
        for (int i = 0; i <10 ; i++) {
            String barcoMaquina = machine.getBarco();
            while(!posicionarflota(tableroPosMaquina, machine.getCoordenadaX(), machine.getCoordenadaY(), machine.getOrientacion(), barcoMaquina)){
            }
        }
    }

    public String[][] getTableroPosMaquina() {
        return tableroPosMaquina;
    }
}