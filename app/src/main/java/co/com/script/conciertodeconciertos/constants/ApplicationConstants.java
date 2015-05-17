package co.com.script.conciertodeconciertos.constants;

import android.content.Context;

import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 06/05/2015.
 */
public class ApplicationConstants {

    //TODO Hardcoded Array strings, just for mock

    private static String[] arrayTexts;
    private static int[] arrayResourcesImages;
    private static final String[] announcments = new String[]{"J Balvin en concierto", "Juanes en Bogotá", "Inna! Sin límites"};
    private static final String[] photos = new String[]{"http://www.billboard.com/files/styles/promo_650/public/media/j-balvin-650-430.jpg?itok=vtt5voQz","http://www.elcolombiano.com/documents/10157/0/640x280/0c0/0d0/none/11101/KXMQ/juanes-conciertos-colombia-640x280-07082014.jpg","http://ultramusic.com/wp-content/uploads/2013/06/inna-2654-2560x1600.jpg"};
    private static final String[] agendaTitles = new String[]{"Inicio presentación J Balvin", "Inicio presetación Daddy Yankee", "Inicio presentación Enrique Iglesias", "Pautado Juanes", "Pautada Inna", "Juanes nuevamente", "Enrique para finalizar"};
    private static final String[] firstHourTitles =  new String[]{"05","07","09","10","11","11","12"};
    private static final String[] secondHourTitles = new String[]{"00","30","00","30","00","30","00"};
    private static final String[] indicativeTitles = new String[]{"PM","PM","PM","PM","PM","PM","PM"};

    public static String[] getTextListOfMenu(Context mContext){

        arrayTexts = new String[] {mContext.getResources().getString(R.string.menu_noticias),
                mContext.getResources().getString(R.string.menu_mapa),
                mContext.getResources().getString(R.string.menu_agenda),
                mContext.getResources().getString(R.string.menu_playlist),
                mContext.getResources().getString(R.string.menu_color_moment),
                mContext.getResources().getString(R.string.menu_cerrar_sesion),
                mContext.getResources().getString(R.string.menu_acerca),
        };

        return arrayTexts;
    }

    public static int[] getResourcesOfImagesIconsOfMenu(){

        arrayResourcesImages = new int[] {
                R.drawable.noticias,
                R.drawable.mapa,
                R.drawable.agenda,
                R.drawable.playlist,
                R.drawable.color,
                R.drawable.logout,
                R.drawable.about};

        return arrayResourcesImages;
    }

    public static String[] getPhotos(){ return photos; }
    public static String[] getAnnouncments(){ return announcments; }
    public static String[] getAgendaTitles(){ return agendaTitles; }
    public static String[] getFirstHourTitles(){ return firstHourTitles; }
    public static String[] getSecondHourTitles(){ return secondHourTitles; }
    public static String[] getIndicativeTitles(){ return indicativeTitles; }

}
