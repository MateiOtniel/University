package org.example;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BinarySearchTree {

    private int position;

    private TreeNode root;

    public BinarySearchTree(int position) {
        root = null;
        this.position = position;
    }

    public void insert(Pair data) {
        root = insertRec(root, data);
    }

    private TreeNode insertRec(TreeNode root, Pair data) {
        if(root == null) {
            root = new TreeNode(data);
            return root;
        }

        if(data.getKey().compareTo(root.data.getKey()) < 0) {
            root.left = insertRec(root.left, data);
        } else if(data.getKey().compareTo(root.data.getKey()) > 0) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    public boolean search(Pair data) {
        return searchRec(root, data);
    }

    private boolean searchRec(TreeNode root, Pair data) {
        if(root == null) {
            return false;
        }

        if(data.getKey().equals(root.data.getKey())) {
            return true;
        }

        if(data.getKey().compareTo(root.data.getKey()) < 0) {
            return searchRec(root.left, data);
        }

        return searchRec(root.right, data);
    }

    public void setOrder() {
        setOrderRec(root);
    }

    private void setOrderRec(TreeNode root) {
        if(root != null) {
            setOrderRec(root.left);
            root.data.setValue(position++);
            setOrderRec(root.right);
        }
    }

    public void setFIP(List<FIP> fip) {
        setFIPRec(root, fip);
    }

    private void setFIPRec(TreeNode root, List<FIP> fip) {
        if(root != null) {
            setFIPRec(root.left, fip);
//            fip.get(root.data.getKey()).setTsCode(String.valueOf(root.data.getValue()));
            fip.stream()
                .forEach(fipElement -> {
                    if(fipElement.getAtom().equals(root.data.getKey())) {
                        fipElement.setTsCode(String.valueOf(root.data.getValue()));
                    }
                });
            setFIPRec(root.right, fip);
        }
    }

    public void saveToCSV(String path) {
        try(BufferedWriter fout = new BufferedWriter(new FileWriter(path))){
            saveToCSVRec(root, path, fout);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
       
    }

    private void saveToCSVRec(TreeNode root, String path, BufferedWriter fout) {
        if(root != null) {
            saveToCSVRec(root.left, path, fout);
            try {
                fout.write(root.data.getKey() + ":" + root.data.getValue());
                fout.newLine();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
            
            saveToCSVRec(root.right, path, fout);
        }
    }

    static class Pair {
        String key;
        int value;

        public Pair(String key) {
            this.key = key;
            this.value = -1;
        }

        public String getKey() {
            return key;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    static class TreeNode {
        Pair data;
        TreeNode left;
        TreeNode right;

        public TreeNode(Pair data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
}
