package com.joy.ui.permissions;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by Daisw on 2017/11/27.
 */

public class Permissions {

    private Map<String, Integer> permissionResults;

    public Permissions(@NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionResults = new HashMap<>(permissions.length);
        for (int i = 0; i < permissions.length; i++) {
            permissionResults.put(permissions[i], grantResults[i]);
        }
    }

    public int getResult(@NonNull String permission) {
        return permissionResults.get(permission);
    }

    public boolean isGranted(@NonNull String permission) {
        return getResult(permission) == PERMISSION_GRANTED;
    }

    public boolean isDenied(@NonNull String permission) {
        return getResult(permission) == PERMISSION_DENIED;
    }
}
