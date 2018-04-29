import React, { Component } from "react";
import { Table } from 'react-bootstrap';
import "./ReportWorks.css";

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
                <div>
                    {
                        works.map((result, i) => {
                            return(
                                <div className="works" key={i}>
                                    <h2>{result.genre}</h2>
                                    <Table responsive>
                                        <tbody>
                                        <tr><td>Yhteenlaskettu myyntihinta: {result.totalSellingPrice}</td></tr>
                                        <tr><td>Keskiarvoinen myyntihinta: {result.averagePrice}</td></tr>
                                        </tbody>
                                    </Table>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}