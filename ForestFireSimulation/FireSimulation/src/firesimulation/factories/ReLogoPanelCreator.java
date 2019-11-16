package firesimulation.factories;

import firesimulation.relogo.UserGlobalsAndPanelFactory;
import repast.simphony.relogo.factories.AbstractReLogoPanelCreator;

import javax.swing.*;

public class ReLogoPanelCreator extends AbstractReLogoPanelCreator {

    public void addComponents(JPanel parent) {
        UserGlobalsAndPanelFactory ugpf = new UserGlobalsAndPanelFactory();
        ugpf.initialize(parent);
        ugpf.addGlobalsAndPanelComponents();
    }

}
