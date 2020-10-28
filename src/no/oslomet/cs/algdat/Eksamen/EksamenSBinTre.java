package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public final boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi, q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if(verdi.equals(null)){
            return 0;
        }

        int antallForekomster = 0;
        Node<T> p = rot;

        while (p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if(cmp < 0){
                p = p.venstre;
            } else {
               if(cmp == 0) antallForekomster++;
                p = p.høyre;
            }
        }

        return antallForekomster;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (true){
            if (p.venstre != null){
                p = p.venstre;
            } else if(p.høyre != null){
                p = p.høyre;
            } else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if(p.forelder == null){
            return null;
        }
        if(p.høyre == p){
            return p.høyre;
        }
        Node forelder = p.forelder;
        if(forelder.høyre == null || forelder.høyre == p){
            return forelder;
        }

        Node nestePostorden = forelder.høyre;
        while(nestePostorden.venstre != null){
            nestePostorden = nestePostorden.venstre;
        }
        if(nestePostorden.høyre != null){
            while (nestePostorden.høyre !=null){
                nestePostorden = nestePostorden.høyre;
            }
        }
        return nestePostorden;
    }

    public void postorden(Oppgave<? super T> oppgave)
    {
        if (tom()) return;  // tomt tre

        Node<T> p = førstePostorden(rot);

        oppgave.utførOppgave(p.verdi);

        while (true) {
            if(p == rot) break;
            p = nestePostorden(p);
            oppgave.utførOppgave(p.verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p == null) return;

        postordenRecursive(p.venstre, oppgave);
        postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
       ArrayList<T> nodeVerdier = new ArrayList<>();
       Queue<Node<T>> kø= new LinkedList<>();
       kø.offer(rot);                   // legger til rot-node i køen

       while (!kø.isEmpty()){
           Node node = kø.poll();       // tar ut første element fra køen
           if (node == null){
           } else {
               nodeVerdier.add((T)node.verdi);
               kø.offer(node.venstre);
               kø.offer(node.høyre);
           }
       }
       return nodeVerdier;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
