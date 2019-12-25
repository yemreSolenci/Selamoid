package com.example.selamoid;

import android.content.res.AssetManager;
import android.util.Log;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DosyaIslemleri {
    private static int index;
    private static double benzerlik=0, benzerlikOran;

    private static void soruAl(AssetManager assetManager, String mesaj) {
        benzerlik = 0;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(assetManager.open("dialoglar"));

            Element kokElement = doc.getDocumentElement();
            kokElement.normalize();
            NodeList dialoglar = doc.getElementsByTagName("dialog");

            for (int i = 1; i < dialoglar.getLength(); i++) {

                Node dialog = dialoglar.item(i);
                Element sorcev = (Element) dialog;

                if (sorcev.getNodeType() == Node.ELEMENT_NODE) {

                    for (int k=0; k<sorcev.getElementsByTagName("soru").getLength(); k++) {

                        benzerlikOran = karsilastir(mesaj,  sorcev.getElementsByTagName("soru").item(k).getTextContent());
                        if (benzerlik < benzerlikOran) {
                            index = i;
                            benzerlik = benzerlikOran;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", e.getMessage());
        }
    }

    private static String cevapAl(AssetManager assetManager) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(assetManager.open("dialoglar"));

            Element kokElement = doc.getDocumentElement();
            kokElement.normalize();
            NodeList dialoglar = doc.getElementsByTagName("dialog");

            Node dialog;
            if (benzerlik>84)
                if (index == 1) {Date tarih = new Date();
                    SimpleDateFormat dakika = new SimpleDateFormat("HH:mm");
                    return ("Şu an saat " + dakika.format(tarih));

                }else if (index == 2) {Date tarih = new Date();
                    SimpleDateFormat bugun = new SimpleDateFormat("dd MMM yyyy EEE");
                    return ("Bugün " + bugun.format(tarih));
                }
                else
                dialog = dialoglar.item(index);
            else
                dialog = dialoglar.item(0);

            Element sorcev = (Element) dialog;

            if (sorcev.getNodeType() == Node.ELEMENT_NODE) {
                return sorcev.getElementsByTagName("cevap").item(0).getTextContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", e.getMessage());
        }
        return "Hata !!  String bulunamadı";
    }

    public static double karsilastir(String mesaj, String mi) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        mesaj = kelimeduz(mesaj);
        mi = kelimeduz(mi);
        mesaj = mesaj.toLowerCase(); mi = mi.toLowerCase();

        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
        double score = service.score(mesaj, mi)*100;
        Log.i("Benzerlik", String.valueOf(score));
        return score;
    }

    //Türkçe karakter kontrol
    private static String kelimeduz(String str) {
        char[] harfler = str.toCharArray();
        for (int i = 0; i < harfler.length; i++) {

            if (harfler[i] == 'ı') harfler[i] = 'i';
            else if (harfler[i] == 'ü') harfler[i] = 'u';
            else if (harfler[i] == 'ö') harfler[i] = 'o';
            else if (harfler[i] == 'ğ') harfler[i] = 'g';
            else if (harfler[i] == 'ş') harfler[i] = 's';
            else if (harfler[i] == 'ç') harfler[i] = 'c';

            else if (harfler[i] == 'I') harfler[i] = 'i';
            else if (harfler[i] == 'İ') harfler[i] = 'i';
            else if (harfler[i] == 'Ü') harfler[i] = 'u';
            else if (harfler[i] == 'Ö') harfler[i] = 'o';
            else if (harfler[i] == 'Ğ') harfler[i] = 'g';
            else if (harfler[i] == 'Ş') harfler[i] = 's';
            else if (harfler[i] == 'Ç') harfler[i] = 'c';
        }
        return String.valueOf(harfler);
    }

    public static String main(AssetManager assetManager, String mesaj) {

        soruAl(assetManager, mesaj);

        return cevapAl(assetManager);
    }
}