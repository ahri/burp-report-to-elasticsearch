package burp.eventStream;

import common.TestConfig;
import org.junit.Test;


import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ScanStateTest
{

    private static final Config noStaticScanIdConfig = new TestConfig()
    {
        @Override
        public String staticScanId()
        {
            return null;
        }
    };

    private static final Config staticScanIdConfig = new TestConfig()
    {
        @Override
        public String staticScanId()
        {
            return "static scan id";
        }
    };

    @Test(expected=ScanState.InvalidStateException.class)
    public void givenScanState_whenScanIdRequestedBeforeStartEventReceived_thenExpectException() throws Exception
    {
        final ScanState scanState = new ScanState(noStaticScanIdConfig);

        scanState.currentScanId();
    }

    @Test
    public void givenConfigWithNoStaticScanId_whenScanIdRequested_thenExpectUuid() throws Exception
    {
        final ScanState scanState = new ScanState(noStaticScanIdConfig);

        scanState.onScanStart(0);

        UUID.fromString(scanState.currentScanId());
    }

    @Test
    public void givenConfigWithStaticScanId_whenScanIdRequested_thenExpectConfiguredStaticScanId() throws Exception
    {
        final ScanState scanState = new ScanState(staticScanIdConfig);

        scanState.onScanStart(0);

        assertEquals(staticScanIdConfig.staticScanId(), scanState.currentScanId());
    }
}