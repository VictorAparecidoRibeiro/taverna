PrimeFaces.locales['pt_BR'] = {
    messages: {
        'org.hibernate.validator.constraints.NotBlank.message': '{0} não pode estar em branco',
        'javax.validation.constraints.NotNull.message': '{0} não pode ser nulo'
    }
};

PrimeFaces.locales['pt'] = {
    closeText: 'Fechar',
    prevText: 'Anterior',
    nextText: 'Próximo',
    currentText: 'Hoje',
    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
    dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Só Horas',
    timeText: 'Tempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    ampm: false,
    month: 'Mês',
    week: 'Semana',
    day: 'Dia',
    allDayText: 'Fora',
    columnFormat: 'ddd M/D'
};

PrimeFaces.widget.Schedule.prototype._oldInit = PrimeFaces.widget.Schedule.prototype.init;
PrimeFaces.widget.Schedule.prototype.init = function (cfg) {
    cfg.columnFormat = {
        month: 'ddd',
        week: 'ddd D/M',
        day: 'dddd D/M'
    };

    cfg.titleFormat = {
        month: 'MMMM YYYY',
        week: 'D/MMMM YYYY',
        day: 'D/MMMM YYYY'
    };

    this._oldInit.apply(this, arguments);
};

