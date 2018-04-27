import React, { Component } from "react";
import "./Product.css";

export default class ReportUser extends Component {
    constructor(props) {
        super(props);

        let works = this.props;
    }

    componentDidMount() {
        fetch('/management/reports/works')
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
                <div className="product">
                    <p>Genre: {genre}</p>
                    <p>Yhteenlaskettu myyntihinta: {totalSellingPrice}</p>
                    <p>Keskiarvoinen myyntihinta: {averagePrice}</p>
                </div>
            </div>
        );
    }
}