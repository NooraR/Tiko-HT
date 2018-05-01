import React, { Component } from "react";
import { Tabs, Tab } from 'react-bootstrap';
import ReportWorks from "./ReportWorks";
import ReportUsers from "./ReportUsers";
import "./Maintenance.css";

export default class Maintenance extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Tabs defaultActiveKey={1} id="uncontrolled-tab-example">
                    <Tab eventKey={1} title="Myyntihinta-raportti">
                        <ReportWorks />
                    </Tab>
                    <Tab eventKey={2} title="Käyttäjäraportti">
                        <ReportUsers />
                    </Tab>
                </Tabs>;

            </div>
        );
    }
}


