package com.ezio.plugin.navigator.action.curl;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/6
 */
public interface HeaderModifier {
    /**
     * @param header the header to check
     * @return true if header should be modified and false otherwise.
     */
    boolean matches(Header header);

    /**
     * @param header the header to modify
     * @return modified header or null to omit header in curl log
     */
    Header modify(Header header);
}
