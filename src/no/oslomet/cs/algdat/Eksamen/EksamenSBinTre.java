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

    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) {
                rot = b;
                if(b != null) {
                    b.forelder = q;
                }
            }
            else if (p == q.venstre) {
                q.venstre = b;
                if(b != null) {
                    b.forelder = q;
                }
            }
            else {
                q.høyre = b;
                if(b != null) {
                    b.forelder = q;
                }
            }
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;

            if (r.høyre !=null){
                r.forelder.høyre = s;
            }
        }


        antall--;   // det er nå én node mindre i treet
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antallFjernet= 0;
        while (fjern(verdi)){
            antallFjernet++;
        }
        return antallFjernet;
    }

    public int antall(T verdi) {
        if(verdi == null){
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
        if(!tom()){
            int antall = antall();
            if (antall == 1){
                fjern(rot.verdi);
                endringer++;
            }else {
                Node<T> p = rot;
                while (p.venstre != null){
                    p = p.venstre;
                }
                T verdi;

                for (int i = 0; i < antall; i++){
                    if (p != null) {
                        verdi = p.verdi;
                        p = nesteInorden(p);
                        fjern(verdi);
                        endringer++;
                    }
                }
            }
        }
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
        Node<T> forelder = p.forelder;
        if(forelder.høyre == null || forelder.høyre == p){
            return forelder;
        }

        Node<T> nestePostorden = forelder.høyre;
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

        while (p != rot) {
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

    @SuppressWarnings("NonAsciiCharacters")
    public ArrayList<T> serialize() {
       ArrayList<T> nodeVerdier = new ArrayList<>();
       Queue<Node<T>> kø= new LinkedList<>();
       kø.offer(rot);                   // legger til rot-node i køen

       while (!kø.isEmpty()){
           Node<T> node = kø.poll();       // tar ut første element fra køen
           if (node != null) {
               nodeVerdier.add(node.verdi);
               kø.offer(node.venstre);
               kø.offer(node.høyre);
           }
       }
       return nodeVerdier;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);

        for (K datum : data) {
            tre.leggInn(datum);
        }
        return tre;
    }

    // hjelpemetode - inorden
    private static <T> Node<T> nesteInorden(Node<T> p){
        if (p.høyre !=null) {
            p = p.høyre;
            while(p.venstre != null){
                p = p.venstre;

            } return p;

        }else {
            while(p !=null) {
                if(p.forelder != null && p.forelder.høyre !=p){
                    return p.forelder;
                }
                p = p.forelder;
            }

        }
        return null;
    }

} // ObligSBinTre
