package com.example.amirmaharjan.rss.Parsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.amirmaharjan.rss.adaptors.RecyclerAdapters;
import com.example.amirmaharjan.rss.model.LatestNews;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Amir Maharjan on 10/20/2016.
 */

public class ReadRss extends AsyncTask<String , Void, Void> {
    Context context;
    ProgressDialog progressDialog;
    ArrayList<LatestNews> latestNews;
    RecyclerView recyclerView;
    String address;
    URL url;

    public ReadRss(Context context,RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
    }

    protected Void doInBackground(String... params) {
        address=params[0];
        ProcessXml(getdata(address));

        return null;
    }

    private void ProcessXml(Document data) {
        if(data!=null){
            latestNews = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            Log.d("number",""+items.getLength());
            for(int i=0;i<items.getLength();i++) {
                Node currentchild = items.item(i);
                if (currentchild.getNodeName().equalsIgnoreCase("item")) {
                    LatestNews item = new LatestNews();
                    NodeList itemchild = currentchild.getChildNodes();
                    for (int j = 0; j < itemchild.getLength(); j++) {
                        Node current = itemchild.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("thumb")) {
                            item.setThumb(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPublishdate(current.getTextContent());
                        }

                    }
                    latestNews.add(item);

                    Log.d("itemtitle", item.getTitle());
                    Log.d("itemDescription",item.getDescription());
                    Log.d("itemlink",item.getLink());
                    Log.d("itempubDate",item.getPublishdate());
                    Log.d("itemthumb",item.getThumb());
                }
            }
        }
    }



    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        RecyclerAdapters adapter = new RecyclerAdapters(context,latestNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public Document getdata(String address){
        try {
            url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream inputStream = conn.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document xmldocument = documentBuilder.parse(inputStream);
            return xmldocument;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
