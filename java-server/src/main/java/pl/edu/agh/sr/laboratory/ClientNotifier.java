package pl.edu.agh.sr.laboratory;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import pl.edu.agh.sr.rpc.laboratory.Notifier;

public class ClientNotifier {
    private String address;
    private int port;

    private TTransport transport;

    private Notifier.Client notifier;

    public ClientNotifier(String address, int port) {
        this.address = address;
        this.port = port;

        init();
    }

    private void init() {
        TProtocol protocol = null;
        transport = null;

        notifier = null;

        try {
            transport = new TSocket(address, port);
            protocol = new TBinaryProtocol(transport);

            notifier = new Notifier.Client(protocol);

            if (transport != null) {
                transport.open();
            }
        } catch(TException e) {
            e.printStackTrace();
        }
    }

    public void notify(String message) throws TException {
        notifier.notify(message);
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (transport != null) {
            transport.close();
        }
    }
}
