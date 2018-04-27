import React, { Component } from "react";
import "./Maintenance.css";
import ReportWorks from "./ReportWorks";
import ReportUsers from "./ReportUsers";

export default class Maintenance extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <ReportWorks />
                <hr />
                <ReportUsers />
            </div>
        );
    }
}