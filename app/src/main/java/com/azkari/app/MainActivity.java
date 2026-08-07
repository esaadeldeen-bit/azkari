package com.azkari.app;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout box; TextView text, counter; Button next;
    int idx=0; String session="morning";
    String[][] morning={
      {"آية الكرسي","اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ...","مرة"},
      {"الإخلاص","قُلْ هُوَ اللَّهُ أَحَدٌ ۝ اللَّهُ الصَّمَدُ ۝ لَمْ يَلِدْ وَلَمْ يُولَدْ ۝ وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ","3 مرات"},
      {"الفلق","قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ ۝ مِن شَرِّ مَا خَلَقَ...","3 مرات"},
      {"الناس","قُلْ أَعُوذُ بِرَبِّ النَّاسِ ۝ مَلِكِ النَّاسِ ۝ إِلَٰهِ النَّاسِ...","3 مرات"},
      {"ذكر","رَضِيتُ بِاللَّهِ رَبًّا، وَبِالإِسْلَامِ دِينًا، وَبِمُحَمَّدٍ ﷺ نَبِيًّا.","3 مرات"}
    };
    String[][] evening=morning;

    @Override public void onCreate(Bundle b){
      super.onCreate(b);
      session=getIntent().getStringExtra("session"); if(session==null)session="morning";
      buildHome();
      if(Build.VERSION.SDK_INT>=33) requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},55);
      ReminderScheduler.scheduleAll(this);
    }
    TextView tv(String s,int size){ TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setPadding(20,16,20,16);return t; }
    void buildHome(){
      box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL);box.setPadding(25,35,25,25);
      ScrollView sv=new ScrollView(this);sv.addView(box);setContentView(sv);
      box.addView(tv("أذكاري",30)); box.addView(tv(new SimpleDateFormat("EEEE، d MMMM",new Locale("ar")).format(new Date()),14));
      Button m=new Button(this);m.setText("☀️ أذكار الصباح — 7:00 ص");m.setOnClickListener(v->open("morning"));box.addView(m);
      Button e=new Button(this);e.setText("🌙 أذكار المساء — 10:00 م");e.setOnClickListener(v->open("evening"));box.addView(e);
      Button rem=new Button(this);rem.setText("إعادة ضبط مواعيد التنبيه");rem.setOnClickListener(v->{ReminderScheduler.scheduleAll(this);Toast.makeText(this,"تم ضبط التنبيهات اليومية.",Toast.LENGTH_SHORT).show();});box.addView(rem);
    }
    void open(String s){
      session=s;idx=0;box.removeAllViews();box.addView(tv(s.equals("morning")?"☀️ أذكار الصباح":"🌙 أذكار المساء",25));
      text=tv("",20);text.setTextIsSelectable(true);box.addView(text);
      counter=tv("",14);box.addView(counter);
      next=new Button(this);next.setText("تمت القراءة — التالي");next.setOnClickListener(v->next());box.addView(next);show();
    }
    String[][] list(){return session.equals("morning")?morning:evening;}
    void show(){String[] z=list()[idx];text.setText(z[0]+"\n\n"+z[1]+"\n\n"+z[2]);counter.setText((idx+1)+" / "+list().length);}
    void next(){if(idx<list().length-1){idx++;show();}else{Toast.makeText(this,"ما شاء الله، تمت الأذكار.",Toast.LENGTH_LONG).show();buildHome();}}
}
