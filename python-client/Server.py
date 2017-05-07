from threading import Thread

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

from handler import NotifierHandler
from laboratory import Notifier


class Server(Thread):
    def __init__(self, port):
        Thread.__init__(self)
        self.port = port

    def run(self):
        try:
            notifier_handler = NotifierHandler.NotifierHandler()
            processor = Notifier.Processor(notifier_handler)
            transport = TSocket.TServerSocket(port=self.port)
            tfactory = TTransport.TBufferedTransportFactory()
            pfactory = TBinaryProtocol.TBinaryProtocolFactory()

            server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
            server.serve()
        except (SystemExit, KeyboardInterrupt):
            return
