import React, { Component } from "react";
import Product from './Product';
import "./Search.css";

export default class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            search: '',
            works: [],
        };

        this.handleChange = this.handleChange.bind(this);
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

    handleClick(e) {

    }

    render() {
        let search = this.state.search;
        let works = this.state.works;

        if (search !== null) {
            if (search !== undefined) {
                if (search.length > 0) {
                    if (works !== undefined) {
                        works = works.filter(function (i) {
                            return i.name.toLowerCase().match(search.toLowerCase())
                                || i.author.toLowerCase().match(search.toLowerCase())
                                || (i.isbn) && i.isbn.match(search.toString())
                                || (i.published) && i.published.toString().match(search.toString())
                                || (i.genre) && i.genre.toLowerCase().match(search.toLowerCase())
                                || (i.type) && i.type.toLowerCase().match(search.toLowerCase())
                        });
                    }}}
        }

        return (
            <div className="SearchContainer">
                <div className="Search">
                    <input type="text" value={this.state.search} onChange={this.handleChange} placeholder="Haku..." />
                </div>
                <div className="ResultContainer">
                    {
                        works.map((result, i) => {
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
