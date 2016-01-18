function imperfection = getImperfection(vehID)
%getImperfection Returns driver's imperfection.
%   imperfection = getImperfection(VEHID) Returns the driver's imperfection
%   (dawdling) [0,1]

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getImperfection.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
imperfection = traci.vehicle.getUniversal(constants.VAR_IMPERFECTION, vehID);