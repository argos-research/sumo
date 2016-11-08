function ContextSubscriptionResults = getContextSubscriptionResults(viewID)
%getContextSubscriptionResults Get the context subscription results for the
%   last time step.
%   contextSubscriptionResults = getContextSubscriptionResults(VIEWID) 
%   Returns the context subscription results for the last time step and the
%   given view. If no view id is given, all subscription results are 
%   returned in a containers.Map data struccure. If the view id is unknown 
%   or the subscription did for any reason return no data, 'None' is 
%   returned.
%   It is not possible to retrieve older subscription results than the ones
%   from the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getContextSubscriptionResults.m 31 2016-09-28 15:16:56Z afacostag $

global guiSubscriptionResults
if isempty(guiSubscriptionResults)
    throw(MException('traci:FatalTraCIError',...
        'You have to subscribe to the variable'));
end
if nargin < 1
    viewID=None;
end
ContextSubscriptionResults = guiSubscriptionResults.getContext(viewID);