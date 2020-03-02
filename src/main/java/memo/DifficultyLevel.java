package memo;

public enum DifficultyLevel {
    LEVEL1(1, 4, 2, 5, 1),
    LEVEL2(2, 4, 3, 4, 2),
    LEVEL3(3, 4, 4, 3, 3),
    LEVEL4(4, 4, 5, 2, 4),
    LEVEL5(5, 4, 6, 1, 5);

    private Integer level;
    private Integer boardX;
    private Integer boardY;
    private Integer showTime;
    private Integer pointsMultiplier;

    DifficultyLevel(Integer level, Integer boardX, Integer boardY, Integer showTime, Integer pointsMultiplier) {
        this.level = level;
        this.boardX = boardX;
        this.boardY = boardY;
        this.showTime = showTime;
        this.pointsMultiplier = pointsMultiplier;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getBoardX() {
        return boardX;
    }

    public Integer getBoardY() {
        return boardY;
    }

    public Integer getShowTime() {
        return showTime;
    }

    public Integer getPointsMultiplier() {
        return pointsMultiplier;
    }
}
