package org.mcnative.actionframework.client.discovery;

import com.spotify.dns.DnsSrvResolver;
import com.spotify.dns.DnsSrvResolvers;
import com.spotify.dns.LookupResult;
import com.spotify.dns.statistics.DnsReporter;
import com.spotify.dns.statistics.DnsTimingContext;
import net.pretronic.libraries.utility.GeneralUtil;
import net.pretronic.libraries.utility.Iterators;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class DnsServiceDiscovery implements ServiceDiscovery{

    private static final DnsReporter REPORTER = new StdoutReporter();

    private final String dns;
    private final DnsSrvResolver resolver;

    public DnsServiceDiscovery(String dns) {
        this.dns = dns;
        this.resolver = DnsSrvResolvers.newBuilder()
                .retainingDataOnFailures(true)
                .metered(REPORTER)
                .dnsLookupTimeoutMillis(1000)
                .build();
    }

    public static void main(String[] args) {
    }

    @Override
    public List<InetSocketAddress> getAddresses() {
        List<LookupResult> result = new ArrayList<>(resolver.resolve(this.dns));
        result.sort((o1, o2) -> {
            if(o1.priority() == o2.priority()){
                return GeneralUtil.getDefaultRandom().nextInt(2) == 0 ? -1 : 1;
            }
            return Integer.compare(o1.priority(),o2.priority());
        });
        return Iterators.map(result, result1 -> new InetSocketAddress(result1.host(), result1.port()));
    }

    private static class StdoutReporter implements DnsReporter {
        @Override
        public DnsTimingContext resolveTimer() {
            return () -> {};
        }

        @Override
        public void reportFailure(Throwable error) {
            error.printStackTrace(System.err);
        }

        @Override
        public void reportEmpty() {}
    }
}
