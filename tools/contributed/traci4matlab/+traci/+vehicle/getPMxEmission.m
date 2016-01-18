function PMxEmission = getPMxEmission(vehID)
%getPmxEmission Get the particular matter emission of the vehicle.
%   pmxEmission = getPmxEmission(VEHID) Returns the particular matter 
%   emission in mg for the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPMxEmission.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
PMxEmission = traci.vehicle.getUniversal(constants.VAR_PMXEMISSION, vehID);