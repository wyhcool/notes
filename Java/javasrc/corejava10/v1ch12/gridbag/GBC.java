import java.awt.*;

/**
 * 辅助类
 * This class simplifies the use of the GridBagConstraints class.
 *
 * @version 1.0.0 2020-03-12 20:26
 * @author bruce
 */
public class GBC extends GridBagConstraints {

    /**
     * Constructs a GBC with given gridx and gridy position
     * and all other grid bag constraint values set to the default
     *
     * @param gridx the gridx position
     * @param gridy the gridy position
     */
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * Constructs a GBC with given gridx, gridy, gridwidth and gridheight
     * and all other grid bag constraint values set to the default
     *
     * @param gridx the gridx position
     * @param gridy the gridy position
     * @param gridwidth the cell span in x-direction
     * @param gridheight the cell span in y-direction
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    /**
     * sets the anchor
     *
     * @param anchor the anchor value
     * @return this object for further modification
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * sets the fill direction
     *
     * @param fill the fill direction
     * @return this object for further modification
     */
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    /**
     * sets the cell weights
     *
     * @param weightx the cell weight in x-direction
     * @param weighty the cell weight in y-direction
     * @return this object for further modification
     */
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * sets the insets of this cell
     *
     * @param distance the spacing to use in all directions
     * @return this object for further modification
     */
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * sets the insets of this cell
     *
     * @param top the spacing to use on the top
     * @param left the spacing to use on the left
     * @param bottom the spacing to use on the bottom
     * @param right the spacing to use on the right
     * @return this object for further modification
     */
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * sets the internal padding
     *
     * @param ipadx the internal padding in x-direction
     * @param ipady the internal padding in y-direction
     * @return
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
