package Model;

import java.util.List;

public class mainModel {
  List<portfolio> portfoliosList;


  /*
  constructor
  initializes portfoliosList to null
   */

  public mainModel() {
    this.portfoliosList = null;
  }

  public void CreateNewPortfolio(portfolio newPortfolio) {
    portfoliosList.add(newPortfolio);
  }





}
