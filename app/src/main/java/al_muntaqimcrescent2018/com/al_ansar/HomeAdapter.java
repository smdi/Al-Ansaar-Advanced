package al_muntaqimcrescent2018.com.al_ansar;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Imran on 31-01-2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {



    private List<HomeInitialiser> listitem;
    private Context context;
    private  boolean change = true,liked = false;
    private int position,pos;

    public HomeAdapter(Context context,List<HomeInitialiser> listitem) {
        this.listitem = listitem;
        this.context = context;
    }

       public String getCapsHead(String heading) {


        StringBuffer  str = new StringBuffer();

        String build = new String();
        String fullName = ""+heading;

        String []bank = fullName.split("\\s");

        String token ,remain;


        if(true) {

            try {
                for(int i = 0 ;i<bank.length ;i++){

                    token = bank[i].substring(0,1);

                    remain = bank[i].substring(1,bank[i].length());

                    str.append(" "+build.concat(token.toUpperCase()+remain));

                    System.out.print(" "+build.concat(token.toUpperCase()+remain));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return " "+str;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView  headings ,dates ,more;
        public TextView  descriptions;
        public ImageView cimages;
        public RelativeLayout relay;
        public ImageView share,follow;
        public WebView webView;


        public ViewHolder(View itemView) {
            super(itemView);

            dates = (TextView) itemView.findViewById(R.id.date);
            more = (TextView) itemView.findViewById(R.id.more);
            headings = (TextView) itemView.findViewById(R.id.main_head);
            descriptions = (TextView) itemView.findViewById(R.id.description);
            cimages = (ImageView) itemView.findViewById(R.id.CircularImageOntop);
            relay = (RelativeLayout) itemView.findViewById(R.id.Layout_inCard);
            share = (ImageView) itemView.findViewById(R.id.share);
            follow = (ImageView) itemView.findViewById(R.id.follow);
            webView = (WebView) itemView.findViewById(R.id.watsapp);

        }
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    public StringBuilder getTheTime(String time) {


        StringBuilder stringBuilder = new StringBuilder();
        String split[]  =  time.split("\\s");
        for(int i=0; i<3; i++)
        {

            stringBuilder.append("  "+split[i]);
        }

        stringBuilder.append(" "+split[5]);


        return stringBuilder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HomeInitialiser homeInitialiser = listitem.get(position);

        pos = position+1;

        String headerGet = getCapsHead(homeInitialiser.getHeading());

        holder.headings.setText(""+headerGet);

        holder.headings.setTypeface(Typeface.MONOSPACE);

        holder.descriptions.setText(""+homeInitialiser.getDescription());

        holder.descriptions.setTypeface(Typeface.MONOSPACE);



        boolean isphoto = homeInitialiser.getUri() != null;


        if(isphoto)
        {

            Glide.with(holder.cimages.getContext()).load(homeInitialiser.getUri()).into(holder.cimages);
        }
        else {

            holder.cimages.setVisibility(View.GONE);

        }

          holder.more.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  if (change) {

                      holder.descriptions.setMaxLines(Integer.MAX_VALUE);
                      holder.more.setText("show less");
                      change = false;
                  } else {

                      holder.more.setText("show more");
                      change = true;
                      holder.descriptions.setMaxLines(3);

                  }
              }
          });

         holder.follow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 holder.webView.loadUrl("https://chat.whatsapp.com/IFYGuOQouNUF1pdD3g1uXR");

             }
         });


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final HomeInitialiser homeInitialiser1 = listitem.get(pos);

              Toast.makeText(context ,""+homeInitialiser.getUri(),Toast.LENGTH_SHORT).show();

             Intent share =    shareImageData(context ,""+homeInitialiser1.getHeading(), ""+homeInitialiser1.getUri() ,""+homeInitialiser1.getDescription());

                context.startActivity(Intent.createChooser(share, "choose one"));

            }
        });


        holder.dates.setText(""+getTheTime(homeInitialiser.getDate()));

        holder.cimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = context.getSharedPreferences("delfab",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Intent intent = new Intent(context,Fundamentals.class);
                editor.putInt("del",0);
                editor.commit();
                intent.putExtra("link",""+homeInitialiser.getUri());
                context.startActivity(intent);
            }
        });


    }

    public static Intent shareImageData(Context context, String header, String link, String description) {

//        Intent  shareIntent  =  new Intent(Intent.ACTION_SEND);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        if (Build.VERSION.SDK_INT  < Build.VERSION_CODES.LOLLIPOP) {

              shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }
        else {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }

        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, header);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, header);
        String sAux =" بسم الله الرحمن الرحيم " + "\n\n" + "Al-Ansaar Recommendations\n\n" +header+"\n";
        sAux = sAux + "";
        sAux = sAux+"\n"+description+"\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, sAux);


        return shareIntent;
    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }
}
