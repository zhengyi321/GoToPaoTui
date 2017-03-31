package com.zoutu.gotolibrary.DBCache.XCCacheManager.xccache;

/**https://github.com/jczmdeveloper/XCCacheManager
 * Created by caizhiming on 2015/12/4.
 */
public interface Cache {
    String get(final String key);
    void put(final String key, final String value);
    boolean remove(final String key);
}
