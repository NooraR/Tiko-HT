import React, { Component } from "react";
import Product from './Product';
import "./Search.css";

export default class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            search: 'Elecktran',
            works: [],
        };
    }

    componentDidMount() {
        fetch('/api/data/works')
            .then(results => {
                return results.json();
            }).then(data => {
                let works = data.data;
                this.setState({works: works});
            }
        )
    }

    handleChange = (e) => {
        this.setState({ search: e.target.value });
    };

    render() {
        this.search = this.state.search;

        console.log({search: this.search});
        if (this.state.search != null && this.state.search) {

            if (this.search.length > 0) {
                this.works = this.works.filter(function (i) {
                    return i.name.toLowerCase().match(this.search);
                });
            }
        }
        else {
            console.log("Ei hakukriteerejä");
        }

        return (
            <div className="SearchContainer">
                <div className="Search">
                    <input type="text" value={this.state.search} onChange={this.handleChange} placeholder="Haku..." />
                </div>
                <div className="ResultContainer">
                    {
                        this.state.works.map((result, i) => {
                            return (
                                <Product key={i} id={result.id} author={result.author} name={result.name} isbn={result.isbn} published={result.published} genre={result.genre} type={result.type} weight={result.weight} products={result.products} />
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}