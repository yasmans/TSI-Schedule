package lv.tsi.calendar.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String REFERENCE_DATA_CACHE = "referenceDataCache";
    public static final String EVENTS_CACHE = "eventsCache";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        GuavaCache referenceDataCache = new GuavaCache(REFERENCE_DATA_CACHE, CacheBuilder.newBuilder()
                .maximumSize(45)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build());
        GuavaCache eventsCache = new GuavaCache(EVENTS_CACHE, CacheBuilder.newBuilder()
                // 128 MB of memory on 64bit JVM can fit approximately 663 Event objects
                .maximumSize(663)
                .expireAfterAccess(24, TimeUnit.HOURS)
                .build());
        simpleCacheManager.setCaches(Arrays.asList(referenceDataCache, eventsCache));
        return simpleCacheManager;
    }
}
