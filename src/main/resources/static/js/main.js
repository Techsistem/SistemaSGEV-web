// SGEV-WEB - Integracao Front-End com o Back-End Spring REST (Fetch API)
const API_BASE = '/api';

document.addEventListener('DOMContentLoaded', () => {
    initMasks();
    initClientesPage();
    initProdutosPage();
    initVendasPage();
});

function initMasks() {
    const cpfInput = document.getElementById('cpf');
    if (cpfInput) {
        cpfInput.addEventListener('input', (e) => {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 11) v = v.slice(0, 11);
            if (v.length > 9) v = v.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, '$1.$2.$3-$4');
            else if (v.length > 6) v = v.replace(/^(\d{3})(\d{3})(\d+)/, '$1.$2.$3');
            else if (v.length > 3) v = v.replace(/^(\d{3})(\d+)/, '$1.$2');
            e.target.value = v;
        });
    }

    const telInput = document.getElementById('telefone');
    if (telInput) {
        telInput.addEventListener('input', (e) => {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 11) v = v.slice(0, 11);
            if (v.length > 10) v = v.replace(/^(\d{2})(\d{5})(\d{4})$/, '($1) $2-$3');
            else if (v.length > 6) v = v.replace(/^(\d{2})(\d{4})(\d+)/, '($1) $2-$3');
            else if (v.length > 2) v = v.replace(/^(\d{2})(\d+)/, '($1) $2');
            e.target.value = v;
        });
    }

    const cepInput = document.getElementById('cep');
    if (cepInput) {
        cepInput.addEventListener('input', (e) => {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 8) v = v.slice(0, 8);
            if (v.length > 5) v = v.replace(/^(\d{5})(\d{3})$/, '$1-$2');
            e.target.value = v;
        });
    }
}

// 1. Modulo de Clientes SGEV-WEB
function initClientesPage() {
    const formCliente = document.getElementById('form-cliente');
    const alertSuccess = document.getElementById('alert-success');
    const alertError = document.getElementById('alert-error');
    const tabelaClientes = document.getElementById('tabela-clientes-corpo');

    if (tabelaClientes) carregarClientes();

    if (formCliente) {
        formCliente.addEventListener('submit', async (e) => {
            e.preventDefault();
            if (alertSuccess) alertSuccess.style.display = 'none';
            if (alertError) alertError.style.display = 'none';

            const clienteData = {
                nome: document.getElementById('nome')?.value.trim() || '',
                cpf: document.getElementById('cpf')?.value || '',
                email: document.getElementById('email')?.value.trim() || '',
                telefone: document.getElementById('telefone')?.value || '',
                cep: document.getElementById('cep')?.value || '',
                cidade: document.getElementById('cidade')?.value.trim() || '',
                uf: document.getElementById('uf')?.value || ''
            };

            try {
                const response = await fetch(`${API_BASE}/clientes`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(clienteData)
                });

                const data = await response.json().catch(() => ({}));

                if (!response.ok) {
                    throw new Error(data.erro || 'Falha ao cadastrar cliente');
                }

                if (alertSuccess) {
                    alertSuccess.textContent = 'Cliente salvo com sucesso no banco de dados MySQL!';
                    alertSuccess.style.display = 'block';
                }
                formCliente.reset();
                carregarClientes();
            } catch (err) {
                if (alertError) {
                    alertError.textContent = err.message;
                    alertError.style.display = 'block';
                } else {
                    alert(err.message);
                }
            }
        });
    }
}

async function carregarClientes() {
    const tabela = document.getElementById('tabela-clientes-corpo');
    if (!tabela) return;

    try {
        const response = await fetch(`${API_BASE}/clientes`);
        if (!response.ok) throw new Error('Erro HTTP: ' + response.status);
        const clientes = await response.json();

        tabela.innerHTML = '';
        clientes.forEach(c => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${c.id}</td>
                <td>${c.nome}</td>
                <td>${c.cpf}</td>
                <td>${c.email || '-'}</td>
                <td>${c.telefone || '-'}</td>
                <td>${c.cidade || '-'}/${c.uf || '-'}</td>
            `;
            tabela.appendChild(tr);
        });
    } catch (e) {
        console.error('Erro ao listar clientes:', e);
    }
}

// 2. Modulo de Produtos SGEV-WEB (Corrigido)
function initProdutosPage() {
    const formProduto = document.getElementById('form-produto');
    const tabelaProdutos = document.getElementById('tabela-produtos-corpo');

    if (tabelaProdutos) carregarProdutos();

    if (formProduto) {
        formProduto.addEventListener('submit', async (e) => {
            e.preventDefault();

            const campoDesc = document.getElementById('prod-descricao');
            const campoPreco = document.getElementById('prod-preco');
            const campoEstoque = document.getElementById('prod-estoque');

            const desc = campoDesc ? campoDesc.value.trim() : '';
            const preco = campoPreco ? parseFloat(campoPreco.value) : NaN;
            const estoque = campoEstoque ? parseInt(campoEstoque.value, 10) : NaN;

            if (!desc || isNaN(preco) || isNaN(estoque)) {
                alert('Preencha a descrição, preço e estoque corretamente.');
                return;
            }

            const produtoData = {
                descricao: desc,
                preco: preco,
                estoque: estoque
            };

            try {
                const res = await fetch(`${API_BASE}/produtos`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(produtoData)
                });

                if (res.ok) {
                    alert('Produto gravado com sucesso no MySQL!');
                    formProduto.reset();
                    carregarProdutos();
                } else {
                    const err = await res.json().catch(() => ({}));
                    alert('Falha ao cadastrar produto: ' + (err.erro || res.statusText));
                }
            } catch (err) {
                console.error(err);
                alert('Erro de conexão ao salvar produto: ' + err.message);
            }
        });
    }
}

async function carregarProdutos() {
    const tabela = document.getElementById('tabela-produtos-corpo');
    if (!tabela) return;

    try {
        const res = await fetch(`${API_BASE}/produtos`);
        if (!res.ok) throw new Error('Erro HTTP: ' + res.status);
        const produtos = await res.json();

        tabela.innerHTML = '';
        produtos.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>P00${p.id}</td>
                <td>${p.descricao}</td>
                <td>R$ ${parseFloat(p.preco).toFixed(2)}</td>
                <td>${p.estoque} un</td>
            `;
            tabela.appendChild(tr);
        });
    } catch (e) {
        console.error('Erro ao listar produtos:', e);
    }
}

// 3. Modulo de Vendas SGEV-WEB Integrado com DescontoService
let itensVenda = [];
let subtotalVenda = 0;

function initVendasPage() {
    const formItem = document.getElementById('form-item-venda');
    const selectCliente = document.getElementById('cliente-venda');
    const selectProduto = document.getElementById('produto-select');

    if (selectCliente && selectProduto) {
        popularSelectsVenda();
    }

    if (formItem) {
        formItem.addEventListener('submit', (e) => {
            e.preventDefault();
            const produtoId = selectProduto.value;
            const produtoTexto = selectProduto.options[selectProduto.selectedIndex].text;
            const preco = parseFloat(selectProduto.options[selectProduto.selectedIndex].dataset.preco);
            const qtd = parseInt(document.getElementById('quantidade').value, 10);

            if (!produtoId || isNaN(preco) || qtd <= 0) {
                alert('Selecione um produto e quantidade válida');
                return;
            }

            const totalItem = preco * qtd;
            subtotalVenda += totalItem;
            itensVenda.push({ produtoId, produtoTexto, preco, qtd, totalItem });

            atualizarTabelaVenda();
        });
    }
}

async function popularSelectsVenda() {
    const selCli = document.getElementById('cliente-venda');
    const selProd = document.getElementById('produto-select');
    if (!selCli || !selProd) return;

    try {
        const resCli = await fetch(`${API_BASE}/clientes`);
        const clientes = await resCli.json();
        selCli.innerHTML = '<option value="">-- Selecione um Cliente --</option>';
        clientes.forEach(c => {
            selCli.innerHTML += `<option value="${c.id}">${c.nome} (CPF: ${c.cpf})</option>`;
        });

        const resProd = await fetch(`${API_BASE}/produtos`);
        const produtos = await resProd.json();
        selProd.innerHTML = '<option value="">-- Selecione um Produto --</option>';
        produtos.forEach(p => {
            selProd.innerHTML += `<option value="${p.id}" data-preco="${p.preco}">${p.descricao} - R$ ${parseFloat(p.preco).toFixed(2)}</option>`;
        });
    } catch (e) {
        console.error('Erro ao carregar listas de vendas:', e);
    }
}

function atualizarTabelaVenda() {
    const corpo = document.getElementById('tabela-itens-corpo');
    if (!corpo) return;

    corpo.innerHTML = '';
    itensVenda.forEach(i => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${i.produtoTexto}</td>
            <td>R$ ${i.preco.toFixed(2)}</td>
            <td>${i.qtd}</td>
            <td>R$ ${i.totalItem.toFixed(2)}</td>
        `;
        corpo.appendChild(tr);
    });

    let taxa = 0;
    if (subtotalVenda > 500) taxa = 0.10;
    else if (subtotalVenda >= 100) taxa = 0.05;

    const desc = subtotalVenda * taxa;
    const total = subtotalVenda - desc;

    const subEl = document.getElementById('resumo-subtotal');
    const descEl = document.getElementById('resumo-desconto');
    const totEl = document.getElementById('resumo-total');

    if (subEl) subEl.textContent = `R$ ${subtotalVenda.toFixed(2)}`;
    if (descEl) descEl.textContent = `R$ ${desc.toFixed(2)} (${(taxa * 100)}%)`;
    if (totEl) totEl.textContent = `R$ ${total.toFixed(2)}`;
}

async function finalizarVendaBackend() {
    const clienteSelect = document.getElementById('cliente-venda');
    const clienteId = clienteSelect ? clienteSelect.value : null;

    if (!clienteId || itensVenda.length === 0) {
        alert('Selecione um cliente e ao menos um produto no carrinho.');
        return;
    }

    const payload = {
        clienteId: clienteId,
        subtotal: subtotalVenda
    };

    try {
        const res = await fetch(`${API_BASE}/vendas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            alert('Venda gravada com sucesso no MySQL com o desconto aplicado!');
            location.reload();
        } else {
            const err = await res.json().catch(() => ({}));
            alert('Erro ao gravar venda: ' + (err.erro || 'Falha'));
        }
    } catch (e) {
        alert('Erro ao conectar com o servidor Spring Boot.');
    }
}