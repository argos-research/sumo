function subscribe(varargin) 
%subscribe Subscribe to simulation variable.
%   subscribe() Subscribe to the VAR_DEPARTED_VEHICLES_IDS value for
%   the maximum allowed interval.
%   subscribe(VARIDS) Subscribe to the values given in the cell 
%   array of strings VARIDS for the maximum allowed interval.
%   subscribe(...,BEGIN) Subscribe from the time BEGIN to the maximum 
%   allowed end time.
%   subscribe(...,END) Subscribe for the time interval defined by BEGIN and 
%   END.
%   A call to this function clears all previous subscription results.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: subscribe.m 31 2016-09-28 15:16:56Z afacostag $

global simSubscriptionResults
import traci.constants

% Parse the input
p = inputParser;
p.FunctionName = 'simulation.subscribe';
p.addOptional('varIDs', {constants.VAR_DEPARTED_VEHICLES_IDS}, @iscell)
p.addOptional('subscriptionBegin', 0, @(x)isnumeric(x) && length(x)==1)
p.addOptional('subscriptionEnd', 2^31-1, @(x)isnumeric(x) && length(x)==1)
p.parse(varargin{:})
varIDs = p.Results.varIDs;
subscriptionBegin = p.Results.subscriptionBegin;
subscriptionEnd = p.Results.subscriptionEnd;

simSubscriptionResults = traci.SubscriptionResults(traci.RETURN_VALUE_FUNC.simulation);

simSubscriptionResults.reset()
traci.subscribe(constants.CMD_SUBSCRIBE_SIM_VARIABLE,...
    subscriptionBegin, subscriptionEnd, 'x', varIDs)