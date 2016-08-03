package ru.enjoyflowers.shop.server;

import ru.enjoy.server.data.Root;

/**
 * Created by dm on 29.05.16.
 */
public class LocalDataLoader {

    private static final String LocalCopyFileName = "EnjoyData.json.gz";

    private DataLoader dataLoader;

    public LocalDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public boolean load(IOnLoadListener listener){
        return true;
    }

    public static interface IOnLoadListener {
        void OnLoad(Root root);
    }

}
