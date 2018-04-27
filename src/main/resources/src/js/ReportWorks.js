import React, { Component } from "react";
import "./Product.css";

export default class ReportWorks extends Component {
    constructor(props) {
        super(props);

        this.state = {
            works: []
        };
    }

    componentDidMount() {
        fetch('/management/reports/works', {
            credentials: "same-origin"
        })
            .then(results => {
                return results.json();
            }).then(data => {
                let works = data.data;
                this.setState({works: works});
            }
        )
    }


    render() {
        let works = this.state.works;

        return (
            <div>
                <h1>Myyntihinnat</h1>
                <div className="product">
                    {
                        works.map((result, i) => {
                            return(
                                <div key={i}>
                                    <p>Genre: {result.genre}</p>
                                    <p>Yhteenlaskettu myyntihinta: {result.totalSellingPrice}</p>
                                    <p>Keskiarvoinen myyntihinta: {result.averagePrice}</p>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}