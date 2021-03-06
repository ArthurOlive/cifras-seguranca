package br.edu.ufersa.anselmos;

import br.edu.ufersa.anselmos.config.CifraConfig;
import br.edu.ufersa.anselmos.config.ServerConfig;
import br.edu.ufersa.anselmos.core.cifra.CifraRC4;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class.getName());

    public static void main(String[] args) {
        var cifra = new CifraRC4(CifraConfig.CHAVE, CifraConfig.SAL, Base64.getEncoder(), Base64.getDecoder());
        try {
            var registry = LocateRegistry.createRegistry(ServerConfig.PORTA);
            var server = new ConsoleServerLogger(cifra);
            registry.bind(ServerConfig.NOME, server);
            logger.log(Level.SEVERE, "Started");
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "Não foi possível obter o registry");
        } catch (AlreadyBoundException e) {
            logger.log(Level.SEVERE, "Já existe uma instância do servidor cadastrada para '" + ServerConfig.NOME + "'");
        }
    }
}
