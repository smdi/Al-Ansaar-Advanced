package al_muntaqimcrescent2018.com.al_ansar;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.File;
import java.net.URL;
import java.util.List;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

//import at.huber.youtubeExtractor.YouTubeUriExtractor;
//import at.huber.youtubeExtractor.YtFile;

/**
 * Created by Imran on 08-02-2018.
 */

public class Video_Audio_Adapter extends RecyclerView.Adapter<Video_Audio_Adapter.ViewHolder> {

    private int pos;
    private Context context;
    private List<Video_Audio_Initialiser> listitem;

    public Video_Audio_Adapter(Context context, List<Video_Audio_Initialiser> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list,parent,false);
        return new ViewHolder(v);
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
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        pos = position+1;

        final Video_Audio_Initialiser video_audio_initialiser = listitem.get(position);

//        String headerGet = getCapsHead(""+video_audio_initialiser.getDescription());

        Toast.makeText(context,""+video_audio_initialiser.getDescription(),Toast.LENGTH_SHORT).show();

        holder.mediades.setText(""+video_audio_initialiser.getDescription());

//        Toast.makeText(context,"header\n"+headerGet,Toast.LENGTH_SHORT).show();

        StringBuilder date = getTheTime(""+video_audio_initialiser.getDate());

        holder.datemedia.setText(""+date);

        final String vid = getVid(""+video_audio_initialiser.getUri());

        holder.videoView.setWebViewClient(new MyBrowser(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                holder.progressBar.setVisibility(View.GONE);
            }
        });

        holder.videoView.loadUrl("http://www.youtube.com/embed/"+vid+"?autoplay=1&vq=large"+"&rel=0");


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AV_display.class);
                intent.putExtra("link",""+video_audio_initialiser.getUri());
                context.startActivity(intent);
            }
        });

        holder.shareview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Video_Audio_Initialiser homeInitialiser = listitem.get(pos);

                Toast.makeText(context ,""+homeInitialiser.getUri(),Toast.LENGTH_SHORT).show();

                Intent share =    shareImageData(context ,"Al-Ansaar video recommendation", ""+homeInitialiser.getUri() ,""+homeInitialiser.getDescription());

                context.startActivity(Intent.createChooser(share, "choose one"));

            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context ,"download",Toast.LENGTH_SHORT).show();

                final Video_Audio_Initialiser homeInitialiser = listitem.get(pos);

                Toast.makeText(context ,""+homeInitialiser.getUri(),Toast.LENGTH_SHORT).show();


                try {

                    YouTubeUriExtractor ytEx = new YouTubeUriExtractor(context) {
                        @Override
                        public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                            if (ytFiles != null) {
                                int itag = 22;

                                String downloadUrl = ytFiles.get(itag).getUrl();

                                setURl(downloadUrl);
                                
                            }
                        }
                    };

                    ytEx.execute(homeInitialiser.getUri());




                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        holder.videoView.getSettings().setPluginState(WebSettings.PluginState.ON);
        holder.videoView.getSettings().setJavaScriptEnabled(true);
        holder.videoView.setVisibility(View.VISIBLE);
        holder.videoView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        holder.videoView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        holder.videoView.getSettings().setAppCacheEnabled(true);
        holder.videoView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        holder.videoView.setScrollbarFadingEnabled(true);
        holder.webSettings.setDomStorageEnabled(true);
        holder.webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.webSettings.getUseWideViewPort();
        holder.webSettings.setLoadWithOverviewMode(true);
        holder.webSettings.setUseWideViewPort(true);
        holder.webSettings.setSupportZoom(true);
        holder.webSettings.getSaveFormData();
        holder.webSettings.setEnableSmoothTransition(true);
//        holder.videoView.getSettings().setJavaScriptEnabled(true);
        holder.videoView.setVisibility(View.VISIBLE);
//        holder.videoView.getSettings().setBuiltInZoomControls(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            holder.videoView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }
        else{
            holder.videoView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }
    }

    private void setURl(String downloadUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        request.allowScanningByMediaScanner();

        Toast.makeText(context,"pulling visiblity",Toast.LENGTH_SHORT).show();

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"Al-Ansaar");

        Toast.makeText(context,"checking viruses",Toast.LENGTH_SHORT).show();

        DownloadManager dm = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

        dm.enqueue(request);

        Toast.makeText(context ,"downloading file",Toast.LENGTH_SHORT).show();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public WebView videoView;
        public ProgressBar progressBar;
        public RelativeLayout relativeLayout ,mainrelay;
        public TextView mediades,datemedia;
        public ImageButton imageView,shareview,download;
        public WebSettings webSettings;
        public ViewHolder(View itemView) {
            super(itemView);

            download = (ImageButton) itemView.findViewById(R.id.downloadOption);
            shareview = (ImageButton) itemView.findViewById(R.id.share);
            imageView = (ImageButton) itemView.findViewById(R.id.enlarge);
            videoView = (WebView) itemView.findViewById(R.id.CircularImageOntop);
            mediades  = (TextView)  itemView.findViewById(R.id.descriptionmedia);
            datemedia = (TextView)  itemView.findViewById(R.id.datemedia);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.Layout_inCard);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadvideo);
            webSettings = videoView.getSettings();
            mainrelay = (RelativeLayout) itemView.findViewById(R.id.mainrelay);
        }
    }


    public String getVid(String text) {

        String yt = ""+text;
        final String []ty ;

        final String []you = yt.split("=");

        if(you[1].contains("&"))
        {
            ty = you[1].split("&");


            System.out.println(""+ty[0]);

            return ty[0];
        }

        else{


            System.out.println(""+you[1]);

            return you[1];
        }

    }
    public static Intent shareImageData(Context context, String header, String link, String description) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        if (Build.VERSION.SDK_INT  < Build.VERSION_CODES.LOLLIPOP) {

            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }
        else {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }

        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, header);
        String sAux =""+header+"\n\n";
        sAux = sAux + ""+link;
        sAux = sAux+"";
        shareIntent.putExtra(Intent.EXTRA_TEXT, sAux);


        return shareIntent;
    }
}
