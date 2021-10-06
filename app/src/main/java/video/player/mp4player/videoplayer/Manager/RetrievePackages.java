package video.player.mp4player.videoplayer.Manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class RetrievePackages {
	
	private Context _ctx;
	
	public RetrievePackages(Context ctx) {
		_ctx = ctx;
	}
	
    public ArrayList<PInfo> getPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }
        return apps;
    }

    public ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();        
        List<PackageInfo> packs = _ctx.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            if ( ( (p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true)
            {
            	PInfo newInfo = new PInfo();
                newInfo.appname = p.applicationInfo.loadLabel(_ctx.getPackageManager()).toString();
                newInfo.pname = p.packageName;
                newInfo.versionName = p.versionName;
                newInfo.versionCode = p.versionCode;
                newInfo.icon = p.applicationInfo.loadIcon(_ctx.getPackageManager());
                res.add(newInfo);
            }
        }
        return res; 
    }
}
