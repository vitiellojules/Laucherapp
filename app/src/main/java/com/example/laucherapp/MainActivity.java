package com.example.laucherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout appGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appGrid = findViewById(R.id.appGrid);

        loadInstalledApps();
    }

    private void loadInstalledApps() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo info : appList) {
            String appName = info.loadLabel(packageManager).toString();
            Drawable appIcon = info.activityInfo.loadIcon(packageManager);

            // Crie um ícone de aplicativo na grade
            createAppIcon(appName, appIcon);
        }
    }

    private void createAppIcon(final String appName, Drawable appIcon) {
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(appIcon);

        // Defina um clique longo para lançar o aplicativo
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                launchApp(appName);
                return true;
            }
        });

        // Adicione a visualização de ícone do aplicativo à grade
        appGrid.addView(imageView);
    }

    private void launchApp(String appName) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }
}
