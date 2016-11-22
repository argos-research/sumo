function subscriptionResults = getSubscriptionResults()
%getSubscriptionResults Get the subscription results for the last time step.
%   subscriptionResults = getSubscriptionResults() Returns the subscription
%   results for the last time step.
%   It is not possible to retrieve older subscription results than the ones
%   from the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSubscriptionResults.m 31 2016-09-28 15:16:56Z afacostag $

global simSubscriptionResults
if isempty(simSubscriptionResults)
    throw(MException('traci:FatalTraCIError',...
        'You have to subscribe to the variable'));
end

subscriptionResults = simSubscriptionResults.get('x');