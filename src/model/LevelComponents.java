package model;

public enum LevelComponents {
    N(1), S(2), E(4), W(8),
    NS(3), NE(5), NSE(7), NW(9), NSW(11), NEW(13),
    SE(6),SW(10), SEW(14), EW(12), NSEW(15);
    LevelComponents(int rep){ representation = rep; }
    public final int representation;
}
