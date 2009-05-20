package ged.gui;

import ged.editoperation.EditOperation;
import ged.editpath.EditPath;
import ged.editpath.NodeEditPath;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

class EditOperationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	EditOperationsPanel(EditPath editPath) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(editPath);
		
		JTree tree = new JTree(root);
		
		for(NodeEditPath nodeEditPath : editPath.getNodeEditPaths()) {
			DefaultMutableTreeNode editPathNode = new DefaultMutableTreeNode(nodeEditPath);
			root.add(editPathNode);
			
			for(EditOperation editOperation : nodeEditPath.getEditOperations()) {
				DefaultMutableTreeNode editOperationNode = new DefaultMutableTreeNode(editOperation);
				editPathNode.add(editOperationNode);
			}
		}
		
		tree.expandRow(0);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(300, 400));
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
				
		add(scrollPane, constraints);
	}

}
